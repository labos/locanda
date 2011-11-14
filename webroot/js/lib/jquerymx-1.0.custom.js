
//jquery.lang.js

(function( $ ) {
	// Several of the methods in this plugin use code adapated from Prototype
	//  Prototype JavaScript framework, version 1.6.0.1
	//  (c) 2005-2007 Sam Stephenson
	var regs = {
		undHash: /_|-/,
		colons: /::/,
		words: /([A-Z]+)([A-Z][a-z])/g,
		lowUp: /([a-z\d])([A-Z])/g,
		dash: /([a-z\d])([A-Z])/g,
		replacer: /\{([^\}]+)\}/g,
		dot: /\./
	},
		getNext = function(current, nextPart, add){
			return current[nextPart] || ( add && (current[nextPart] = {}) );
		},
		isContainer = function(current){
			var type = typeof current;
			return type && (  type == 'function' || type == 'object' );
		},
		getObject = function( objectName, roots, add ) {
			
			var parts = objectName ? objectName.split(regs.dot) : [],
				length =  parts.length,
				currents = $.isArray(roots) ? roots : [roots || window],
				current,
				ret, 
				i,
				c = 0,
				type;
			
			if(length == 0){
				return currents[0];
			}
			while(current = currents[c++]){
				for (i =0; i < length - 1 && isContainer(current); i++ ) {
					current = getNext(current, parts[i], add);
				}
				if( isContainer(current) ) {
					
					ret = getNext(current, parts[i], add); 
					
					if( ret !== undefined ) {
						
						if ( add === false ) {
							delete current[parts[i]];
						}
						return ret;
						
					}
					
				}
			}
		},

		/** 
		 * @class jQuery.String
		 * 
		 * A collection of useful string helpers.
		 * 
		 */
		str = $.String = $.extend( $.String || {} , {
			/**
			 * @function
			 * Gets an object from a string.
			 * @param {String} name the name of the object to look for
			 * @param {Array} [roots] an array of root objects to look for the name
			 * @param {Boolean} [add] true to add missing objects to 
			 *  the path. false to remove found properties. undefined to 
			 *  not modify the root object
			 */
			getObject : getObject,
			/**
			 * Capitalizes a string
			 * @param {String} s the string.
			 * @return {String} a string with the first character capitalized.
			 */
			capitalize: function( s, cache ) {
				return s.charAt(0).toUpperCase() + s.substr(1);
			},
			/**
			 * Capitalizes a string from something undercored. Examples:
			 * @codestart
			 * jQuery.String.camelize("one_two") //-> "oneTwo"
			 * "three-four".camelize() //-> threeFour
			 * @codeend
			 * @param {String} s
			 * @return {String} a the camelized string
			 */
			camelize: function( s ) {
				s = str.classize(s);
				return s.charAt(0).toLowerCase() + s.substr(1);
			},
			/**
			 * Like camelize, but the first part is also capitalized
			 * @param {String} s
			 * @return {String} the classized string
			 */
			classize: function( s , join) {
				var parts = s.split(regs.undHash),
					i = 0;
				for (; i < parts.length; i++ ) {
					parts[i] = str.capitalize(parts[i]);
				}

				return parts.join(join || '');
			},
			/**
			 * Like [jQuery.String.classize|classize], but a space separates each 'word'
			 * @codestart
			 * jQuery.String.niceName("one_two") //-> "One Two"
			 * @codeend
			 * @param {String} s
			 * @return {String} the niceName
			 */
			niceName: function( s ) {
				str.classize(parts[i],' ');
			},

			/**
			 * Underscores a string.
			 * @codestart
			 * jQuery.String.underscore("OneTwo") //-> "one_two"
			 * @codeend
			 * @param {String} s
			 * @return {String} the underscored string
			 */
			underscore: function( s ) {
				return s.replace(regs.colons, '/').replace(regs.words, '$1_$2').replace(regs.lowUp, '$1_$2').replace(regs.dash, '_').toLowerCase();
			},
			/**
			 * Returns a string with {param} replaced values from data.
			 * 
			 *     $.String.sub("foo {bar}",{bar: "far"})
			 *     //-> "foo far"
			 *     
			 * @param {String} s The string to replace
			 * @param {Object} data The data to be used to look for properties.  If it's an array, multiple
			 * objects can be used.
			 * @param {Boolean} [remove] if a match is found, remove the property from the object
			 */
			sub: function( s, data, remove ) {
				var obs = [];
				obs.push(s.replace(regs.replacer, function( whole, inside ) {
					//convert inside to type
					var ob = getObject(inside, data, typeof remove == 'boolean' ? !remove : remove),
						type = typeof ob;
					if((type === 'object' || type === 'function') && type !== null){
						obs.push(ob);
						return "";
					}else{
						return ""+ob;
					}
				}));
				return obs.length <= 1 ? obs[0] : obs;
			}
		});

})(jQuery);

//jquery.class.js

(function( $ ) {

	// if we are initializing a new class
	var initializing = false,
		makeArray = $.makeArray,
		isFunction = $.isFunction,
		isArray = $.isArray,
		extend = $.extend,
		concatArgs = function(arr, args){
			return arr.concat(makeArray(args));
		},
		// tests if we can get super in .toString()
		fnTest = /xyz/.test(function() {
			xyz;
		}) ? /\b_super\b/ : /.*/,
		// overwrites an object with methods, sets up _super
		// newProps - new properties
		// oldProps - where the old properties might be
		// addTo - what we are adding to
		inheritProps = function( newProps, oldProps, addTo ) {
			addTo = addTo || newProps
			for ( var name in newProps ) {
				// Check if we're overwriting an existing function
				addTo[name] = isFunction(newProps[name]) && 
							  isFunction(oldProps[name]) && 
							  fnTest.test(newProps[name]) ? (function( name, fn ) {
					return function() {
						var tmp = this._super,
							ret;

						// Add a new ._super() method that is the same method
						// but on the super-class
						this._super = oldProps[name];

						// The method only need to be bound temporarily, so we
						// remove it when we're done executing
						ret = fn.apply(this, arguments);
						this._super = tmp;
						return ret;
					};
				})(name, newProps[name]) : newProps[name];
			}
		},


	/**
	 * @class jQuery.Class
	 * @plugin jquery/class
	 * @tag core
	 * @download dist/jquery/jquery.class.js
	 * @test jquery/class/qunit.html
	 * 
	 * Class provides simulated inheritance in JavaScript. Use clss to bridge the gap between
	 * jQuery's functional programming style and Object Oriented Programming. It 
	 * is based off John Resig's [http://ejohn.org/blog/simple-javascript-inheritance/|Simple Class]
	 * Inheritance library.  Besides prototypal inheritance, it includes a few important features:
	 * 
	 *   - Static inheritance
	 *   - Introspection
	 *   - Namespaces
	 *   - Setup and initialization methods
	 *   - Easy callback function creation
	 * 
	 * 
	 * ## Static v. Prototype
	 * 
	 * Before learning about Class, it's important to
	 * understand the difference between
	 * a class's __static__ and __prototype__ properties.
	 * 
	 *     //STATIC
	 *     MyClass.staticProperty  //shared property
	 *     
	 *     //PROTOTYPE
	 *     myclass = new MyClass()
	 *     myclass.prototypeMethod() //instance method
	 * 
	 * A static (or class) property is on the Class constructor
	 * function itself
	 * and can be thought of being shared by all instances of the 
	 * Class. Prototype propertes are available only on instances of the Class.
	 * 
	 * ## A Basic Class
	 * 
	 * The following creates a Monster class with a
	 * name (for introspection), static, and prototype members.
	 * Every time a monster instance is created, the static
	 * count is incremented.
	 *
	 * @codestart
	 * $.Class.extend('Monster',
	 * /* @static *|
	 * {
	 *   count: 0
	 * },
	 * /* @prototype *|
	 * {
	 *   init: function( name ) {
	 *
	 *     // saves name on the monster instance
	 *     this.name = name;
	 *
	 *     // sets the health
	 *     this.health = 10;
	 *
	 *     // increments count
	 *     this.Class.count++;
	 *   },
	 *   eat: function( smallChildren ){
	 *     this.health += smallChildren;
	 *   },
	 *   fight: function() {
	 *     this.health -= 2;
	 *   }
	 * });
	 *
	 * hydra = new Monster('hydra');
	 *
	 * dragon = new Monster('dragon');
	 *
	 * hydra.name        // -> hydra
	 * Monster.count     // -> 2
	 * Monster.shortName // -> 'Monster'
	 *
	 * hydra.eat(2);     // health = 12
	 *
	 * dragon.fight();   // health = 8
	 *
	 * @codeend
	 *
	 * 
	 * Notice that the prototype <b>init</b> function is called when a new instance of Monster is created.
	 * 
	 * 
	 * ## Inheritance
	 * 
	 * When a class is extended, all static and prototype properties are available on the new class.
	 * If you overwrite a function, you can call the base class's function by calling
	 * <code>this._super</code>.  Lets create a SeaMonster class.  SeaMonsters are less
	 * efficient at eating small children, but more powerful fighters.
	 * 
	 * 
	 *     Monster.extend("SeaMonster",{
	 *       eat: function( smallChildren ) {
	 *         this._super(smallChildren / 2);
	 *       },
	 *       fight: function() {
	 *         this.health -= 1;
	 *       }
	 *     });
	 *     
	 *     lockNess = new SeaMonster('Lock Ness');
	 *     lockNess.eat(4);   //health = 12
	 *     lockNess.fight();  //health = 11
	 * 
	 * ### Static property inheritance
	 * 
	 * You can also inherit static properties in the same way:
	 * 
	 *     $.Class.extend("First",
	 *     {
	 *         staticMethod: function() { return 1;}
	 *     },{})
	 *
	 *     First.extend("Second",{
	 *         staticMethod: function() { return this._super()+1;}
	 *     },{})
	 *
	 *     Second.staticMethod() // -> 2
	 * 
	 * ## Namespaces
	 * 
	 * Namespaces are a good idea! We encourage you to namespace all of your code.
	 * It makes it possible to drop your code into another app without problems.
	 * Making a namespaced class is easy:
	 * 
	 * @codestart
	 * $.Class.extend("MyNamespace.MyClass",{},{});
	 *
	 * new MyNamespace.MyClass()
	 * @codeend
	 * <h2 id='introspection'>Introspection</h2>
	 * Often, it's nice to create classes whose name helps determine functionality.  Ruby on
	 * Rails's [http://api.rubyonrails.org/classes/ActiveRecord/Base.html|ActiveRecord] ORM class
	 * is a great example of this.  Unfortunately, JavaScript doesn't have a way of determining
	 * an object's name, so the developer must provide a name.  Class fixes this by taking a String name for the class.
	 * @codestart
	 * $.Class.extend("MyOrg.MyClass",{},{})
	 * MyOrg.MyClass.shortName //-> 'MyClass'
	 * MyOrg.MyClass.fullName //->  'MyOrg.MyClass'
	 * @codeend
	 * The fullName (with namespaces) and the shortName (without namespaces) are added to the Class's
	 * static properties.
	 *
	 *
	 * <h2>Setup and initialization methods</h2>
	 * <p>
	 * Class provides static and prototype initialization functions.
	 * These come in two flavors - setup and init.
	 * Setup is called before init and
	 * can be used to 'normalize' init's arguments.
	 * </p>
	 * <div class='whisper'>PRO TIP: Typically, you don't need setup methods in your classes. Use Init instead.
	 * Reserve setup methods for when you need to do complex pre-processing of your class before init is called.
	 *
	 * </div>
	 * @codestart
	 * $.Class.extend("MyClass",
	 * {
	 *   setup: function() {} //static setup
	 *   init: function() {} //static constructor
	 * },
	 * {
	 *   setup: function() {} //prototype setup
	 *   init: function() {} //prototype constructor
	 * })
	 * @codeend
	 *
	 * <h3>Setup</h3>
	 * <p>Setup functions are called before init functions.  Static setup functions are passed
	 * the base class followed by arguments passed to the extend function.
	 * Prototype static functions are passed the Class constructor function arguments.</p>
	 * <p>If a setup function returns an array, that array will be used as the arguments
	 * for the following init method.  This provides setup functions the ability to normalize
	 * arguments passed to the init constructors.  They are also excellent places
	 * to put setup code you want to almost always run.</p>
	 * <p>
	 * The following is similar to how [jQuery.Controller.prototype.setup]
	 * makes sure init is always called with a jQuery element and merged options
	 * even if it is passed a raw
	 * HTMLElement and no second parameter.
	 * </p>
	 * @codestart
	 * $.Class.extend("jQuery.Controller",{
	 *   ...
	 * },{
	 *   setup: function( el, options ) {
	 *     ...
	 *     return [$(el),
	 *             $.extend(true,
	 *                this.Class.defaults,
	 *                options || {} ) ]
	 *   }
	 * })
	 * @codeend
	 * Typically, you won't need to make or overwrite setup functions.
	 * <h3>Init</h3>
	 *
	 * <p>Init functions are called after setup functions.
	 * Typically, they receive the same arguments
	 * as their preceding setup function.  The Foo class's <code>init</code> method
	 * gets called in the following example:
	 * </p>
	 * @codestart
	 * $.Class.Extend("Foo", {
	 *   init: function( arg1, arg2, arg3 ) {
	 *     this.sum = arg1+arg2+arg3;
	 *   }
	 * })
	 * var foo = new Foo(1,2,3);
	 * foo.sum //-> 6
	 * @codeend
	 * <h2>Callbacks</h2>
	 * <p>Similar to jQuery's proxy method, Class provides a
	 * [jQuery.Class.static.callback callback]
	 * function that returns a callback to a method that will always
	 * have
	 * <code>this</code> set to the class or instance of the class.
	 * </p>
	 * The following example uses this.callback to make sure
	 * <code>this.name</code> is available in <code>show</code>.
	 * @codestart
	 * $.Class.extend("Todo",{
	 *   init: function( name ) { this.name = name }
	 *   get: function() {
	 *     $.get("/stuff",this.callback('show'))
	 *   },
	 *   show: function( txt ) {
	 *     alert(this.name+txt)
	 *   }
	 * })
	 * new Todo("Trash").get()
	 * @codeend
	 * <p>Callback is available as a static and prototype method.</p>
	 * <h2>Demo</h2>
	 * @demo jquery/class/class.html
	 *
	 * @constructor Creating a new instance of an object that has extended jQuery.Class
	 *     calls the init prototype function and returns a new instance of the class.
	 *
	 */

	clss = $.Class = function() {
		if (arguments.length) {
			clss.extend.apply(clss, arguments);
		}
	};

	/* @Static*/
	extend(clss, {
		/**
		 * @function callback
		 * Returns a callback function for a function on this Class.
		 * The callback function ensures that 'this' is set appropriately.  
		 * @codestart
		 * $.Class.extend("MyClass",{
		 *     getData: function() {
		 *         this.showing = null;
		 *         $.get("data.json",this.callback('gotData'),'json')
		 *     },
		 *     gotData: function( data ) {
		 *         this.showing = data;
		 *     }
		 * },{});
		 * MyClass.showData();
		 * @codeend
		 * <h2>Currying Arguments</h2>
		 * Additional arguments to callback will fill in arguments on the returning function.
		 * @codestart
		 * $.Class.extend("MyClass",{
		 *    getData: function( <b>callback</b> ) {
		 *      $.get("data.json",this.callback('process',<b>callback</b>),'json');
		 *    },
		 *    process: function( <b>callback</b>, jsonData ) { //callback is added as first argument
		 *        jsonData.processed = true;
		 *        callback(jsonData);
		 *    }
		 * },{});
		 * MyClass.getData(showDataFunc)
		 * @codeend
		 * <h2>Nesting Functions</h2>
		 * Callback can take an array of functions to call as the first argument.  When the returned callback function
		 * is called each function in the array is passed the return value of the prior function.  This is often used
		 * to eliminate currying initial arguments.
		 * @codestart
		 * $.Class.extend("MyClass",{
		 *    getData: function( callback ) {
		 *      //calls process, then callback with value from process
		 *      $.get("data.json",this.callback(['process2',callback]),'json') 
		 *    },
		 *    process2: function( type,jsonData ) {
		 *        jsonData.processed = true;
		 *        return [jsonData];
		 *    }
		 * },{});
		 * MyClass.getData(showDataFunc);
		 * @codeend
		 * @param {String|Array} fname If a string, it represents the function to be called.  
		 * If it is an array, it will call each function in order and pass the return value of the prior function to the
		 * next function.
		 * @return {Function} the callback function.
		 */
		callback: function( funcs ) {

			//args that should be curried
			var args = makeArray(arguments),
				self;

			funcs = args.shift();

			if (!isArray(funcs) ) {
				funcs = [funcs];
			}

			self = this;
			
			return function class_cb() {
				var cur = concatArgs(args, arguments),
					isString, 
					length = funcs.length,
					f = 0,
					func;

				for (; f < length; f++ ) {
					func = funcs[f];
					if (!func ) {
						continue;
					}

					isString = typeof func == "string";
					if ( isString && self._set_called ) {
						self.called = func;
					}
					cur = (isString ? self[func] : func).apply(self, cur || []);
					if ( f < length - 1 ) {
						cur = !isArray(cur) || cur._use_call ? [cur] : cur
					}
				}
				return cur;
			}
		},
		/**
		 *   @function getObject 
		 *   Gets an object from a String.
		 *   If the object or namespaces the string represent do not
		 *   exist it will create them.  
		 *   @codestart
		 *   Foo = {Bar: {Zar: {"Ted"}}}
		 *   $.Class.getobject("Foo.Bar.Zar") //-> "Ted"
		 *   @codeend
		 *   @param {String} objectName the object you want to get
		 *   @param {Object} [current=window] the object you want to look in.
		 *   @return {Object} the object you are looking for.
		 */
		getObject: $.String.getObject,
		/**
		 * @function newInstance
		 * Creates a new instance of the class.  This method is useful for creating new instances
		 * with arbitrary parameters.
		 * <h3>Example</h3>
		 * @codestart
		 * $.Class.extend("MyClass",{},{})
		 * var mc = MyClass.newInstance.apply(null, new Array(parseInt(Math.random()*10,10))
		 * @codeend
		 * @return {class} instance of the class
		 */
		newInstance: function() {
			var inst = this.rawInstance(),
				args;
			if ( inst.setup ) {
				args = inst.setup.apply(inst, arguments);
			}
			if ( inst.init ) {
				inst.init.apply(inst, isArray(args) ? args : arguments);
			}
			return inst;
		},
		/**
		 * Setup gets called on the inherting class with the base class followed by the
		 * inheriting class's raw properties.
		 * 
		 * Setup will deeply extend a static defaults property on the base class with 
		 * properties on the base class.  For example:
		 * 
		 *     $.Class("MyBase",{
		 *       defaults : {
		 *         foo: 'bar'
		 *       }
		 *     },{})
		 * 
		 *     MyBase("Inheriting",{
		 *       defaults : {
		 *         newProp : 'newVal'
		 *       }
		 *     },{}
		 *     
		 *     Inheriting.defaults -> {foo: 'bar', 'newProp': 'newVal'}
		 * 
		 * @param {Object} baseClass the base class that is being inherited from
		 * @param {String} fullName the name of the new class
		 * @param {Object} staticProps the static properties of the new class
		 * @param {Object} protoProps the prototype properties of the new class
		 */
		setup: function( baseClass, fullName ) {
			this.defaults = extend(true, {}, baseClass.defaults, this.defaults);
			return arguments;
		},
		rawInstance: function() {
			initializing = true;
			var inst = new this();
			initializing = false;
			return inst;
		},
		/**
		 * Extends a class with new static and prototype functions.  There are a variety of ways
		 * to use extend:
		 * @codestart
		 * //with className, static and prototype functions
		 * $.Class.extend('Task',{ STATIC },{ PROTOTYPE })
		 * //with just classname and prototype functions
		 * $.Class.extend('Task',{ PROTOTYPE })
		 * //With just a className
		 * $.Class.extend('Task')
		 * @codeend
		 * @param {String} [fullName]  the classes name (used for classes w/ introspection)
		 * @param {Object} [klass]  the new classes static/class functions
		 * @param {Object} [proto]  the new classes prototype functions
		 * @return {jQuery.Class} returns the new class
		 */
		extend: function( fullName, klass, proto ) {
			// figure out what was passed
			if ( typeof fullName != 'string' ) {
				proto = klass;
				klass = fullName;
				fullName = null;
			}
			if (!proto ) {
				proto = klass;
				klass = null;
			}

			proto = proto || {};
			var _super_class = this,
				_super = this.prototype,
				name, shortName, namespace, prototype;

			// Instantiate a base class (but only create the instance,
			// don't run the init constructor)
			initializing = true;
			prototype = new this();
			initializing = false;
			// Copy the properties over onto the new prototype
			inheritProps(proto, _super, prototype);

			// The dummy class constructor

			function Class() {
				// All construction is actually done in the init method
				if ( initializing ) return;

				if ( this.constructor !== Class && arguments.length ) { //we are being called w/o new
					return arguments.callee.extend.apply(arguments.callee, arguments)
				} else { //we are being called w/ new
					return this.Class.newInstance.apply(this.Class, arguments)
				}
			}
			// Copy old stuff onto class
			for ( name in this ) {
				if ( this.hasOwnProperty(name) ) {
					Class[name] = this[name];
				}
			}

			// copy new props on class
			inheritProps(klass, this, Class);

			// do namespace stuff
			if ( fullName ) {

				var parts = fullName.split(/\./),
					shortName = parts.pop(),
					current = clss.getObject(parts.join('.'), window, true),
					namespace = current;

				
				current[shortName] = Class;
			}

			// set things that can't be overwritten
			extend(Class, {
				prototype: prototype,
				namespace: namespace,
				shortName: shortName,
				constructor: Class,
				fullName: fullName
			});

			//make sure our prototype looks nice
			Class.prototype.Class = Class.prototype.constructor = Class;


			/**
			 * @attribute fullName 
			 * The full name of the class, including namespace, provided for introspection purposes.
			 * @codestart
			 * $.Class.extend("MyOrg.MyClass",{},{})
			 * MyOrg.MyClass.shortName //-> 'MyClass'
			 * MyOrg.MyClass.fullName //->  'MyOrg.MyClass'
			 * @codeend
			 */

			var args = Class.setup.apply(Class, concatArgs([_super_class],arguments));

			if ( Class.init ) {
				Class.init.apply(Class, args || []);
			}

			/* @Prototype*/
			return Class;
			/** 
			 * @function setup
			 * If a setup method is provided, it is called when a new 
			 * instances is created.  It gets passed the same arguments that
			 * were given to the Class constructor function (<code> new Class( arguments ... )</code>).
			 * 
			 *     $.Class("MyClass",
			 *     {
			 *        setup: function( val ) {
			 *           this.val = val;
			 *         }
			 *     })
			 *     var mc = new MyClass("Check Check")
			 *     mc.val //-> 'Check Check'
			 * 
			 * Setup is called before [jQuery.Class.prototype.init init].  If setup 
			 * return an array, those arguments will be used for init. 
			 * 
			 *     $.Class("jQuery.Controller",{
			 *       setup : function(htmlElement, rawOptions){
			 *         return [$(htmlElement), 
			 *                   $.extend({}, this.Class.defaults, rawOptions )] 
			 *       }
			 *     })
			 * 
			 * <div class='whisper'>PRO TIP: 
			 * Setup functions are used to normalize constructor arguments and provide a place for
			 * setup code that extending classes don't have to remember to call _super to
			 * run.
			 * </div>
			 * 
			 * Setup is not defined on $.Class itself, so calling super in inherting classes
			 * will break.  Don't do the following:
			 * 
			 *     $.Class("Thing",{
			 *       setup : function(){
			 *         this._super(); // breaks!
			 *       }
			 *     })
			 * 
			 * @return {Array|undefined} If an array is return, [jQuery.Class.prototype.init] is 
			 * called with those arguments; otherwise, the original arguments are used.
			 */
			//break up
			/** 
			 * @function init
			 * If an <code>init</code> method is provided, it gets called when a new instance
			 * is created.  Init gets called after [jQuery.Class.prototype.setup setup], typically with the 
			 * same arguments passed to the Class 
			 * constructor: (<code> new Class( arguments ... )</code>).  
			 * 
			 *     $.Class("MyClass",
			 *     {
			 *        init: function( val ) {
			 *           this.val = val;
			 *        }
			 *     })
			 *     var mc = new MyClass(1)
			 *     mc.val //-> 1
			 * 
			 * [jQuery.Class.prototype.setup Setup] is able to modify the arguments passed to init.  Read
			 * about it there.
			 * 
			 */
			//Breaks up code
			/**
			 * @attribute Class
			 * References the static properties of the instance's class.
			 * <h3>Quick Example</h3>
			 * @codestart
			 * // a class with a static classProperty property
			 * $.Class.extend("MyClass", {classProperty : true}, {});
			 * 
			 * // a new instance of myClass
			 * var mc1 = new MyClass();
			 * 
			 * //
			 * mc1.Class.classProperty = false;
			 * 
			 * // creates a new MyClass
			 * var mc2 = new mc.Class();
			 * @codeend
			 * Getting static properties via the Class property, such as it's 
			 * [jQuery.Class.static.fullName fullName] is very common.
			 */
		}

	})





	clss.prototype.
	/**
	 * @function callback
	 * Returns a callback function.  This does the same thing as and is described better in [jQuery.Class.static.callback].
	 * The only difference is this callback works
	 * on a instance instead of a class.
	 * @param {String|Array} fname If a string, it represents the function to be called.  
	 * If it is an array, it will call each function in order and pass the return value of the prior function to the
	 * next function.
	 * @return {Function} the callback function
	 */
	callback = clss.callback;


})(jQuery);

//jquery.event.js



//jquery.event.destroyed.js

(function( $ ) {
	/**
	 * @attribute destroyed
	 * @parent specialevents
	 * @download  http://jmvcsite.heroku.com/pluginify?plugins[]=jquery/dom/destroyed/destroyed.js
	 * @test jquery/event/destroyed/qunit.html
	 * Provides a destroyed event on an element.
	 * <p>
	 * The destroyed event is called when the element
	 * is removed as a result of jQuery DOM manipulators like remove, html,
	 * replaceWith, etc. Destroyed events do not bubble, so make sure you don't use live or delegate with destroyed
	 * events.
	 * </p>
	 * <h2>Quick Example</h2>
	 * @codestart
	 * $(".foo").bind("destroyed", function(){
	 *    //clean up code
	 * })
	 * @codeend
	 * <h2>Quick Demo</h2>
	 * @demo jquery/event/destroyed/destroyed.html 
	 * <h2>More Involved Demo</h2>
	 * @demo jquery/event/destroyed/destroyed_menu.html 
	 */

	var oldClean = jQuery.cleanData;

	$.cleanData = function( elems ) {
		for ( var i = 0, elem;
		(elem = elems[i]) !== undefined; i++ ) {
			$(elem).triggerHandler("destroyed");
			//$.event.remove( elem, 'destroyed' );
		}
		oldClean(elems);
	};

})(jQuery);

//jquery.controller.js

(function( $ ) {

	// ------- helpers  ------
	// Binds an element, returns a function that unbinds
	var bind = function( el, ev, callback ) {
		var wrappedCallback,
			binder = el.bind && el.unbind ? el : $(isFunction(el) ? [el] : el);
		//this is for events like >click.
		if ( ev.indexOf(">") === 0 ) {
			ev = ev.substr(1);
			wrappedCallback = function( event ) {
				if ( event.target === el ) {
					callback.apply(this, arguments);
				} 
			};
		}
		binder.bind(ev, wrappedCallback || callback);
		// if ev name has >, change the name and bind
		// in the wrapped callback, check that the element matches the actual element
		return function() {
			binder.unbind(ev, wrappedCallback || callback);
			el = ev = callback = wrappedCallback = null;
		};
	},
		makeArray = $.makeArray,
		isArray = $.isArray,
		isFunction = $.isFunction,
		extend = $.extend,
		Str = $.String,
		// Binds an element, returns a function that unbinds
		delegate = function( el, selector, ev, callback ) {
			$(el).delegate(selector, ev, callback);
			return function() {
				$(el).undelegate(selector, ev, callback);
				el = ev = callback = selector = null;
			};
		},
		binder = function( el, ev, callback, selector ) {
			return selector ? delegate(el, selector, ev, callback) : bind(el, ev, callback);
		},
		/**
		 * moves 'this' to the first argument 
		 */
		shifter = function shifter(cb) {
			return function() {
				return cb.apply(null, [this.nodeName ? $(this) : this].concat(Array.prototype.slice.call(arguments, 0)));
			};
		},
		// matches dots
		dotsReg = /\./g,
		// matches controller
		controllersReg = /_?controllers?/ig,
		//used to remove the controller from the name
		underscoreAndRemoveController = function( className ) {
			return Str.underscore(className.replace("jQuery.", "").replace(dotsReg, '_').replace(controllersReg, ""));
		},
		// checks if it looks like an action
		actionMatcher = /[^\w]/,
		// handles parameterized action names
		parameterReplacer = /\{([^\}]+)\}/g,
		breaker = /^(?:(.*?)\s)?([\w\.\:>]+)$/,
		basicProcessor,
		data = function(el, data){
			return $.data(el, "controllers", data)
		};
	/**
	 * @class jQuery.Controller
	 * @tag core
	 * @plugin jquery/controller
	 * @download  http://jmvcsite.heroku.com/pluginify?plugins[]=jquery/controller/controller.js
	 * @test jquery/controller/qunit.html
	 * 
	 * Controllers organize event handlers using event delegation. 
	 * If something happens in your application (a user click or a [jQuery.Model|Model] instance being updated), 
	 * a controller should respond to it.  
	 * 
	 * Controllers make your code deterministic, reusable, organized and can tear themselves 
	 * down auto-magically. Read about [http://jupiterjs.com/news/writing-the-perfect-jquery-plugin 
	 * the theory behind controller] and 
	 * a [http://jupiterjs.com/news/organize-jquery-widgets-with-jquery-controller walkthrough of its features]
	 * on Jupiter's blog.
	 * 
	 * 
	 * ## Basic Example
	 * 
	 * Instead of
	 * 
	 * @codestart
	 * $(function(){
	 *   $('#tabs').click(someCallbackFunction1)
	 *   $('#tabs .tab').click(someCallbackFunction2)
	 *   $('#tabs .delete click').click(someCallbackFunction3)
	 * });
	 * @codeend
	 * 
	 * do this
	 * 
	 * @codestart
	 * $.Controller('Tabs',{
	 *   click: function() {...},
	 *   '.tab click' : function() {...},
	 *   '.delete click' : function() {...}
	 * })
	 * $('#tabs').tabs();
	 * @codeend
	 * 
	 * ## Tabs Example
	 * 
	 * @demo jquery/controller/controller.html
	 * 
	 * 
	 * ## Using Controller
	 * 
	 * Controller helps you build and organize jQuery plugins.  It can be used
	 * to build simple widgets, like a slider, or organize multiple
	 * widgets into something greater.
	 * 
	 * To understand how to use Controller, you need to understand 
	 * the typical lifecycle of a jQuery widget and how that maps to
	 * controller's functionality:
	 * 
	 * ### A controller class is created.
	 *       
	 *     $.Controller("MyWidget",
	 *     {
	 *       defaults :  {
	 *         message : "Remove Me"
	 *       }
	 *     },
	 *     {
	 *       init : function(rawEl, rawOptions){ 
	 *         this.element.append(
	 *            "<div>"+this.options.message+"</div>"
	 *           );
	 *       },
	 *       "div click" : function(div, ev){ 
	 *         div.remove();
	 *       }  
	 *     }) 
	 *     
	 * This creates a <code>$.fn.my_widget</code> [jquery.controller.plugin jQuery helper function]
	 * that can be used to create a new controller instance on an element.
	 *       
	 * ### An instance of controller is created on an element
	 * 
	 *     $('.thing').my_widget(options) // calls new MyWidget(el, options)
	 * 
	 * This calls <code>new MyWidget(el, options)</code> on 
	 * each <code>'.thing'</code> element.  
	 *     
	 * When a new [jQuery.Class Class] instance is created, it calls the class's
	 * prototype setup and init methods. Controller's [jQuery.Controller.prototype.setup setup]
	 * method:
	 *     
	 *  - Sets [jQuery.Controller.prototype.element this.element] and adds the controller's name to element's className.
	 *  - Merges passed in options with defaults object and sets it as [jQuery.Controller.prototype.options this.options]
	 *  - Saves a reference to the controller in <code>$.data</code>.
	 *  - [jquery.controller.listening Binds all event handler methods].
	 *   
	 * 
	 * ### The controller responds to events
	 * 
	 * Typically, Controller event handlers are automatically bound.  However, there are
	 * multiple ways to [jquery.controller.listening listen to events] with a controller.
	 * 
	 * Once an event does happen, the callback function is always called with 'this' 
	 * referencing the controller instance.  This makes it easy to use helper functions and
	 * save state on the controller.
	 * 
	 * 
	 * ### The widget is destroyed
	 * 
	 * If the element is removed from the page, the 
	 * controller's [jQuery.Controller.prototype.destroy] method is called.
	 * This is a great place to put any additional teardown functionality.
	 * 
	 * You can also teardown a controller programatically like:
	 * 
	 *     $('.thing').my_widget('destroy');
	 * 
	 * ## Todos Example
	 * 
	 * Lets look at a very basic example - 
	 * a list of todos and a button you want to click to create a new todo.
	 * Your HTML might look like:
	 * 
	 * @codestart html
	 * &lt;div id='todos'>
	 *  &lt;ol>
	 *    &lt;li class="todo">Laundry&lt;/li>
	 *    &lt;li class="todo">Dishes&lt;/li>
	 *    &lt;li class="todo">Walk Dog&lt;/li>
	 *  &lt;/ol>
	 *  &lt;a class="create">Create&lt;/a>
	 * &lt;/div>
	 * @codeend
	 * 
	 * To add a mousover effect and create todos, your controller might look like:
	 * 
	 * @codestart
	 * $.Controller.extend('Todos',{
	 *   ".todo mouseover" : function( el, ev ) {
	 *    el.css("backgroundColor","red")
	 *   },
	 *   ".todo mouseout" : function( el, ev ) {
	 *    el.css("backgroundColor","")
	 *   },
	 *   ".create click" : function() {
	 *    this.find("ol").append("&lt;li class='todo'>New Todo&lt;/li>"); 
	 *   }
	 * })
	 * @codeend
	 * 
	 * Now that you've created the controller class, you've must attach the event handlers on the '#todos' div by
	 * creating [jQuery.Controller.prototype.setup|a new controller instance].  There are 2 ways of doing this.
	 * 
	 * @codestart
	 * //1. Create a new controller directly:
	 * new Todos($('#todos'));
	 * //2. Use jQuery function
	 * $('#todos').todos();
	 * @codeend
	 * 
	 * ## Controller Initialization
	 * 
	 * It can be extremely useful to add an init method with 
	 * setup functionality for your widget.
	 * 
	 * In the following example, I create a controller that when created, will put a message as the content of the element:
	 * 
	 * @codestart
	 * $.Controller.extend("SpecialController",
	 * {
	 *   init: function( el, message ) {
	 *      this.element.html(message)
	 *   }
	 * })
	 * $(".special").special("Hello World")
	 * @codeend
	 * 
	 * ## Removing Controllers
	 * 
	 * Controller removal is built into jQuery.  So to remove a controller, you just have to remove its element:
	 * 
	 * @codestart
	 * $(".special_controller").remove()
	 * $("#containsControllers").html("")
	 * @codeend
	 * 
	 * It's important to note that if you use raw DOM methods (<code>innerHTML, removeChild</code>), the controllers won't be destroyed.
	 * 
	 * If you just want to remove controller functionality, call destroy on the controller instance:
	 * 
	 * @codestart
	 * $(".special_controller").controller().destroy()
	 * @codeend
	 * 
	 * ## Accessing Controllers
	 * 
	 * Often you need to get a reference to a controller, there are a few ways of doing that.  For the 
	 * following example, we assume there are 2 elements with <code>className="special"</code>.
	 * 
	 * @codestart
	 * //creates 2 foo controllers
	 * $(".special").foo()
	 * 
	 * //creates 2 bar controllers
	 * $(".special").bar()
	 * 
	 * //gets all controllers on all elements:
	 * $(".special").controllers() //-> [foo, bar, foo, bar]
	 * 
	 * //gets only foo controllers
	 * $(".special").controllers(FooController) //-> [foo, foo]
	 * 
	 * //gets all bar controllers
	 * $(".special").controllers(BarController) //-> [bar, bar]
	 * 
	 * //gets first controller
	 * $(".special").controller() //-> foo
	 * 
	 * //gets foo controller via data
	 * $(".special").data("controllers")["FooController"] //-> foo
	 * @codeend
	 * 
	 * ## Calling methods on Controllers
	 * 
	 * Once you have a reference to an element, you can call methods on it.  However, Controller has
	 * a few shortcuts:
	 * 
	 * @codestart
	 * //creates foo controller
	 * $(".special").foo({name: "value"})
	 * 
	 * //calls FooController.prototype.update
	 * $(".special").foo({name: "value2"})
	 * 
	 * //calls FooController.prototype.bar
	 * $(".special").foo("bar","something I want to pass")
	 * @codeend
	 */
	$.Class("jQuery.Controller",
	/** 
	 * @Static
	 */
	{
		/**
		 * Does 3 things:
		 * <ol>
		 *     <li>Creates a jQuery helper for this controller.</li>
		 *     <li>Calculates and caches which functions listen for events.</li>
		 *     <li> and attaches this element to the documentElement if onDocument is true.</li>
		 * </ol>   
		 * <h3>jQuery Helper Naming Examples</h3>
		 * @codestart
		 * "TaskController" -> $().task_controller()
		 * "Controllers.Task" -> $().controllers_task()
		 * @codeend
		 */
		init: function() {
			// if you didn't provide a name, or are controller, don't do anything
			if (!this.shortName || this.fullName == "jQuery.Controller" ) {
				return;
			}
			// cache the underscored names
			this._fullName = underscoreAndRemoveController(this.fullName);
			this._shortName = underscoreAndRemoveController(this.shortName);

			var controller = this,
				pluginname = this.pluginName || this._fullName,
				funcName, forLint;

			// create jQuery plugin
			if (!$.fn[pluginname] ) {
				$.fn[pluginname] = function( options ) {

					var args = makeArray(arguments),
						//if the arg is a method on this controller
						isMethod = typeof options == "string" && isFunction(controller.prototype[options]),
						meth = args[0];
					return this.each(function() {
						//check if created
						var controllers = data(this),
							//plugin is actually the controller instance
							plugin = controllers && controllers[pluginname];

						if ( plugin ) {
							if ( isMethod ) {
								// call a method on the controller with the remaining args
								plugin[meth].apply(plugin, args.slice(1));
							} else {
								// call the plugin's update method
								plugin.update.apply(plugin, args);
							}

						} else {
							//create a new controller instance
							controller.newInstance.apply(controller, [this].concat(args));
						}
					});
				};
			}

			// make sure listensTo is an array
			
			// calculate and cache actions
			this.actions = {};

			for ( funcName in this.prototype ) {
				if (funcName == 'constructor' || !isFunction(this.prototype[funcName]) ) {
					continue;
				}
				if ( this._isAction(funcName) ) {
					this.actions[funcName] = this._action(funcName);
				}
			}

			/**
			 * @attribute onDocument
			 * Set to true if you want to automatically attach this element to the documentElement.
			 */
			if ( this.onDocument ) {
				forLint = new controller(document.documentElement);
			}
		},
		hookup: function( el ) {
			return new this(el);
		},

		/**
		 * @hide
		 * @param {String} methodName a prototype function
		 * @return {Boolean} truthy if an action or not
		 */
		_isAction: function( methodName ) {
			if ( actionMatcher.test(methodName) ) {
				return true;
			} else {
				return $.inArray(methodName, this.listensTo) > -1 || $.event.special[methodName] || processors[methodName];
			}

		},
		/**
		 * @hide
		 * @param {Object} methodName the method that will be bound
		 * @param {Object} [options] first param merged with class default options
		 * @return {Object} null or the processor and pre-split parts.  
		 * The processor is what does the binding/subscribing.
		 */
		_action: function( methodName, options ) {
			//if we don't have a controller instance, we'll break this guy up later
			parameterReplacer.lastIndex = 0;
			if (!options && parameterReplacer.test(methodName) ) {
				return null;
			}
			var convertedName = options ? Str.sub(methodName, [options, window]) : methodName,
				arr = isArray(convertedName),
				parts = (arr ? convertedName[1] : convertedName).match(breaker),
				event = parts[2],
				processor = processors[event] || basicProcessor;
			return {
				processor: processor,
				parts: parts,
				delegate : arr ? convertedName[0] : undefined
			};
		},
		/**
		 * @attribute processors
		 * An object of {eventName : function} pairs that Controller uses to hook up events
		 * auto-magically.  A processor function looks like:
		 * 
		 *     jQuery.Controller.processors.
		 *       myprocessor = function( el, event, selector, cb, controller ) {
		 *          //el - the controller's element
		 *          //event - the event (myprocessor)
		 *          //selector - the left of the selector
		 *          //cb - the function to call
		 *          //controller - the binding controller
		 *       };
		 * 
		 * This would bind anything like: "foo~3242 myprocessor".
		 * 
		 * The processor must return a function that when called, 
		 * unbinds the event handler.
		 * 
		 * Controller already has processors for the following events:
		 * 
		 *   - change 
		 *   - click 
		 *   - contextmenu 
		 *   - dblclick 
		 *   - focusin
		 *   - focusout
		 *   - keydown 
		 *   - keyup 
		 *   - keypress 
		 *   - mousedown 
		 *   - mouseenter
		 *   - mouseleave
		 *   - mousemove 
		 *   - mouseout 
		 *   - mouseover 
		 *   - mouseup 
		 *   - reset 
		 *   - resize 
		 *   - scroll 
		 *   - select 
		 *   - submit  
		 * 
		 * The following processors always listen on the window or document:
		 * 
		 *   - windowresize
		 *   - windowscroll
		 *   - load
		 *   - unload
		 *   - hashchange
		 *   - ready
		 *   
		 * Which means anytime the window is resized, the following controller will listen to it:
		 *  
		 *     $.Controller('Sized',{
		 *       windowresize : function(){
		 *         this.element.width(this.element.parent().width() / 2);
		 *       }
		 *     });
		 *     
		 *     $('.foo').sized();
		 */
		processors: {},
		/**
		 * @attribute listensTo
		 * A list of special events this controller listens too.  You only need to add event names that
		 * are whole words (ie have no special characters).
		 * 
		 *     $.Controller('TabPanel',{
		 *       listensTo : ['show']
		 *     },{
		 *       'show' : function(){
		 *         this.element.show();
		 *       }
		 *     })
		 *     
		 *     $('.foo').tab_panel().trigger("show");
		 */
		listensTo: [],
		/**
		 * @attribute defaults
		 * A object of name-value pairs that act as default values for a controller's 
		 * [jQuery.Controller.prototype.options options].
		 * 
		 *     $.Controller("Message",
		 *     {
		 *       defaults : {
		 *         message : "Hello World"
		 *       }
		 *     },{
		 *       init : function(){
		 *         this.element.text(this.options.message);
		 *       }
		 *     })
		 *     
		 *     $("#el1").message(); //writes "Hello World"
		 *     $("#el12").message({message: "hi"}); //writes hi
		 */
		defaults: {}
	},
	/** 
	 * @Prototype
	 */
	{
		/**
		 * Setup is where most of controller's magic happens.  It does the following:
		 * 
		 * ### Sets this.element
		 * 
		 * The first parameter passed to new Controller(el, options) is expected to be 
		 * an element.  This gets converted to a jQuery wrapped element and set as
		 * [jQuery.Controller.prototype.element this.element].
		 * 
		 * ### Adds the controller's name to the element's className.
		 * 
		 * Controller adds it's plugin name to the element's className for easier 
		 * debugging.  For example, if your Controller is named "Foo.Bar", it adds
		 * "foo_bar" to the className.
		 * 
		 * ### Saves the controller in $.data
		 * 
		 * A reference to the controller instance is saved in $.data.  You can find 
		 * instances of "Foo.Bar" like: 
		 * 
		 *     $("#el").data("controllers")['foo_bar'].
		 * 
		 * ### Binds event handlers
		 * 
		 * Setup does the event binding described in [jquery.controller.listening Listening To Events].
		 * 
		 * ## API
		 * @param {HTMLElement} element the element this instance operates on.
		 * @param {Object} [options] option values for the controller.  These get added to
		 * this.options.
		 */
		setup: function( element, options ) {
			var funcName, ready, cls = this.Class;

			//want the raw element here
			element = element.jquery ? element[0] : element;

			//set element and className on element
			this.element = $(element).addClass(cls._fullName);

			//set in data
			(data(element) || data(element, {}))[cls._fullName] = this;

			//adds bindings
			this._bindings = [];
			/**
			 * @attribute options
			 * Options is [jQuery.Controller.static.defaults] merged with the 2nd argument
			 * passed to a controller (or the first argument passed to the 
			 * [jquery.controller.plugin controller's jQuery plugin]).
			 * 
			 * For example:
			 * 
			 *     $.Controller("Tabs", 
			 *     {
			 *        defaults : {
			 *          activeClass: "ui-active-state"
			 *        }
			 *     },
			 *     {
			 *        init : function(){
			 *          this.element.addClass(this.options.activeClass);
			 *        }
			 *     })
			 *     
			 *     $("#tabs1").tabs()                         // adds 'ui-active-state'
			 *     $("#tabs2").tabs({activeClass : 'active'}) // adds 'active'
			 *     
			 *  
			 */
			this.options = extend( extend(true, {}, cls.defaults), options);

			//go through the cached list of actions and use the processor to bind
			for ( funcName in cls.actions ) {
				if ( cls.actions.hasOwnProperty(funcName) ) {
					ready = cls.actions[funcName] || cls._action(funcName, this.options);
					this._bindings.push(
					ready.processor(ready.delegate || element, ready.parts[2], ready.parts[1], this.callback(funcName), this));
				}
			}


			/**
			 * @attribute called
			 * String name of current function being called on controller instance.  This is 
			 * used for picking the right view in render.
			 * @hide
			 */
			this.called = "init";

			//setup to be destroyed ... don't bind b/c we don't want to remove it
			//this.element.bind('destroyed', this.callback('destroy'))
			var destroyCB = shifter(this.callback("destroy"));
			this.element.bind("destroyed", destroyCB);
			this._bindings.push(function( el ) {
				//destroyCB.removed = true;
				$(element).unbind("destroyed", destroyCB);
			});

			/**
			 * @attribute element
			 * The controller instance's delegated element. This 
			 * is set by [jQuery.Controller.prototype.setup setup]. It 
			 * is a jQuery wrapped element.
			 * 
			 * For example, if I add MyWidget to a '#myelement' element like:
			 * 
			 *     $.Controller("MyWidget",{
			 *       init : function(){
			 *         this.element.css("color","red")
			 *       }
			 *     })
			 *     
			 *     $("#myelement").my_widget()
			 * 
			 * MyWidget will turn #myelement's font color red.
			 * 
			 * ## Using a different element.
			 * 
			 * Sometimes, you want a different element to be this.element.  A
			 * very common example is making progressively enhanced form widgets.
			 * 
			 * To change this.element, overwrite Controller's setup method like:
			 * 
			 *     $.Controller("Combobox",{
			 *       setup : function(el, options){
			 *          this.oldElement = $(el);
			 *          var newEl = $('<div/>');
			 *          this.oldElement.wrap(newEl);
			 *          this._super(newEl, options);
			 *       },
			 *       init : function(){
			 *          this.element //-> the div
			 *       },
			 *       ".option click" : function(){
			 *         // event handler bound on the div
			 *       },
			 *       destroy : function(){
			 *          var div = this.element; //save reference
			 *          this._super();
			 *          div.replaceWith(this.oldElement);
			 *       }
			 *     }
			 */
			return this.element;
		},
		/**
		 * Bind attaches event handlers that will be removed when the controller is removed.  
		 * This is a good way to attach to an element not in the controller's element.
		 * <br/>
		 * <h3>Examples:</h3>
		 * @codestart
		 * init: function() {
		 *    // calls somethingClicked(el,ev)
		 *    this.bind('click','somethingClicked') 
		 * 
		 *    // calls function when the window is clicked
		 *    this.bind(window, 'click', function(ev){
		 *      //do something
		 *    })
		 * },
		 * somethingClicked: function( el, ev ) {
		 *   
		 * }
		 * @codeend
		 * @param {HTMLElement|jQuery.fn} [el=this.element] The element to be bound
		 * @param {String} eventName The event to listen for.
		 * @param {Function|String} func A callback function or the String name of a controller function.  If a controller
		 * function name is given, the controller function is called back with the bound element and event as the first
		 * and second parameter.  Otherwise the function is called back like a normal bind.
		 * @return {Integer} The id of the binding in this._bindings
		 */
		bind: function( el, eventName, func ) {
			if ( typeof el == 'string' ) {
				func = eventName;
				eventName = el;
				el = this.element;
			}
			return this._binder(el, eventName, func);
		},
		_binder: function( el, eventName, func, selector ) {
			if ( typeof func == 'string' ) {
				func = shifter(this.callback(func));
			}
			this._bindings.push(binder(el, eventName, func, selector));
			return this._bindings.length;
		},
		/**
		 * Delegate will delegate on an elememt and will be undelegated when the controller is removed.
		 * This is a good way to delegate on elements not in a controller's element.<br/>
		 * <h3>Example:</h3>
		 * @codestart
		 * // calls function when the any 'a.foo' is clicked.
		 * this.delegate(document.documentElement,'a.foo', 'click', function(ev){
		 *   //do something
		 * })
		 * @codeend
		 * @param {HTMLElement|jQuery.fn} [element=this.element] the element to delegate from
		 * @param {String} selector the css selector
		 * @param {String} eventName the event to bind to
		 * @param {Function|String} func A callback function or the String name of a controller function.  If a controller
		 * function name is given, the controller function is called back with the bound element and event as the first
		 * and second parameter.  Otherwise the function is called back like a normal bind.
		 * @return {Integer} The id of the binding in this._bindings
		 */
		delegate: function( element, selector, eventName, func ) {
			if ( typeof element == 'string' ) {
				func = eventName;
				eventName = selector;
				selector = element;
				element = this.element;
			}
			return this._binder(element, eventName, func, selector);
		},
		/**
		 * Called if an controller's [jquery.controller.plugin jQuery helper] is called on an element that already has a controller instance
		 * of the same type.  Extends [jQuery.Controller.prototype.options this.options] with the options passed in.  If you overwrite this, you might want to call
		 * this._super.
		 * <h3>Examples</h3>
		 * @codestart
		 * $.Controller.extend("Thing",{
		 * init: function( el, options ) {
		 *    alert('init')
		 * },
		 * update: function( options ) {
		 *    this._super(options);
		 *    alert('update')
		 * }
		 * });
		 * $('#myel').thing(); // alerts init
		 * $('#myel').thing(); // alerts update
		 * @codeend
		 * @param {Object} options
		 */
		update: function( options ) {
			extend(this.options, options);
		},
		/**
		 * Destroy unbinds and undelegates all event handlers on this controller, 
		 * and prevents memory leaks.  This is called automatically
		 * if the element is removed.  You can overwrite it to add your own
		 * teardown functionality:
		 * 
		 *     $.Controller("ChangeText",{
		 *       init : function(){
		 *         this.oldText = this.element.text();
		 *         this.element.text("Changed!!!")
		 *       },
		 *       destroy : function(){
		 *         this.element.text(this.oldText);
		 *         this._super(); //Always call this!
		 *     })
		 * 
		 * You could call destroy manually on an element with ChangeText
		 * added like:
		 * 
		 *     $("#changed").change_text("destroy");
		 *     
		 * ### API
		 */
		destroy: function() {
			if ( this._destroyed ) {
				throw this.Class.shortName + " controller instance has been deleted";
			}
			var self = this,
				fname = this.Class._fullName,
				controllers;
			this._destroyed = true;
			this.element.removeClass(fname);

			$.each(this._bindings, function( key, value ) {
				value(self.element[0]);
			});

			delete this._actions;

			delete this.element.data("controllers")[fname];
			
			$(this).triggerHandler("destroyed"); //in case we want to know if the controller is removed
			this.element = null;
		},
		/**
		 * Queries from the controller's element.
		 * @codestart
		 * ".destroy_all click" : function() {
		 *    this.find(".todos").remove();
		 * }
		 * @codeend
		 * @param {String} selector selection string
		 * @return {jQuery.fn} returns the matched elements
		 */
		find: function( selector ) {
			return this.element.find(selector);
		},
		//tells callback to set called on this.  I hate this.
		_set_called: true
	});

	var processors = $.Controller.processors,

	//------------- PROCESSSORS -----------------------------
	//processors do the binding.  They return a function that
	//unbinds when called.
	//the basic processor that binds events
	basicProcessor = function( el, event, selector, cb, controller ) {
		var c = controller.Class;

		// document controllers use their name as an ID prefix.
		if ( c.onDocument && !/^Main(Controller)?$/.test(c.shortName) && el === controller.element[0]) { //prepend underscore name if necessary
			selector = selector ? "#" + c._shortName + " " + selector : "#" + c._shortName;
		}
		return binder(el, event, shifter(cb), selector);
	};




	//set commong events to be processed as a basicProcessor
	$.each("change click contextmenu dblclick keydown keyup keypress mousedown mousemove mouseout mouseover mouseup reset resize scroll select submit focusin focusout mouseenter mouseleave".split(" "), function( i, v ) {
		processors[v] = basicProcessor;
	});
	/**
	 *  @add jQuery.fn
	 */

	//used to determine if a controller instance is one of controllers
	//controllers can be strings or classes
	var i, isAControllerOf = function( instance, controllers ) {
		for ( i = 0; i < controllers.length; i++ ) {
			if ( typeof controllers[i] == 'string' ? instance.Class._shortName == controllers[i] : instance instanceof controllers[i] ) {
				return true;
			}
		}
		return false;
	};

	/**
	 * @function controllers
	 * Gets all controllers in the jQuery element.
	 * @return {Array} an array of controller instances.
	 */
	$.fn.controllers = function() {
		var controllerNames = makeArray(arguments),
			instances = [],
			controllers, c, cname;
		//check if arguments
		this.each(function() {

			controllers = $.data(this, "controllers");
			for ( cname in controllers ) {
				if ( controllers.hasOwnProperty(cname) ) {
					c = controllers[cname];
					if (!controllerNames.length || isAControllerOf(c, controllerNames) ) {
						instances.push(c);
					}
				}
			}
		});
		return instances;
	};
	/**
	 * @function controller
	 * Gets a controller in the jQuery element.  With no arguments, returns the first one found.
	 * @param {Object} controller (optional) if exists, the first controller instance with this class type will be returned.
	 * @return {jQuery.Controller} the first controller.
	 */
	$.fn.controller = function( controller ) {
		return this.controllers.apply(this, arguments)[0];
	};

})(jQuery);

//jquery.model.js

(function() {
	
	//helper stuff for later.  Eventually, might not need jQuery.
	var underscore = $.String.underscore,
		classize = $.String.classize,
		isArray = $.isArray,
		makeArray = $.makeArray,
		extend = $.extend,
		each = $.each,
		reqType = /GET|POST|PUT|DELETE/i,
		ajax = function(ajaxOb, attrs, success, error, fixture, type, dataType){
			var dataType = dataType || "json",
				src = "",
				tmp;
			if(typeof ajaxOb == "string"){
				var sp = ajaxOb.indexOf(" ")
				if( sp > 2 && sp <7){
					tmp = ajaxOb.substr(0,sp);
					if(reqType.test(tmp)){
						type = tmp;
					}else{
						dataType = tmp;
					}
					src = ajaxOb.substr(sp+1)
				}else{
					src = ajaxOb;
				}
			}
			attrs = extend({},attrs)
			
			var url = $.String.sub(src, attrs, true)
			return $.ajax({
				url : url,
				data : attrs,
				success : success,
				error: error,
				type : type || "post",
				dataType : dataType,
				fixture: fixture
			});
		},
		//guesses at a fixture name
		fixture = function(extra, or){
			var u = underscore( this.shortName ),
				f = "-"+u+(extra||"");
			return $.fixture && $.fixture[f] ? f : or ||
				"//"+underscore( this.fullName )
						.replace(/\.models\..*/,"")
						.replace(/\./g,"/")+"/fixtures/"+u+
						(extra || "")+".json";
		},
		addId = function(attrs, id){
			attrs = attrs || {};
			var identity = this.id;
			if(attrs[identity] && attrs[identity] !== id){
				attrs["new"+$.String.capitalize(id)] = attrs[identity];
				delete attrs[identity];
			}
			attrs[identity] = id;
			return attrs;
		},
		getList = function(type){
			var listType = type || $.Model.List || Array;
			return new listType();
		},
		getId = function(inst){
			return inst[inst.Class.id]
		},
		unique = function(items){
	        var collect = [];
	        for(var i=0; i < items.length; i++){
	            if(!items[i]["__u Nique"]){
	                collect.push(items[i]);
	                items[i]["__u Nique"] = true;
	            }
	        }
	        for(i=0; i< collect.length; i++){
	            delete collect[i]["__u Nique"];
	        }
	        return collect;
	    },
		// makes a deferred request
		makeRequest = function(self, type, success, error, method){
			var deferred = $.Deferred(),
				resolve = function(data){
					self[method || type+"d"](data);
					deferred.resolveWith(self,[self, data, type]);
				},
				reject = function(data){
					deferred.rejectWith(self, [data])
				},
				args = [self.attrs(), resolve, reject];
				
			if(type == 'destroy'){
				args.shift();
			}	
				
			if(type !== 'create' ){
				args.unshift(getId(self))
			} 
			
			deferred.then(success);
			deferred.fail(error);
			
			self.Class[type].apply(self.Class, args);
				
			return deferred.promise();
		},
		// a quick way to tell if it's an object and not some string
		isObject = function(obj){
			return typeof obj === 'object' && obj !== null && obj;
		},
		$method = function(name){
			return function( eventType, handler ) {
				$.fn[name].apply($([this]), arguments);
				return this;
			}
		},
		bind = $method('bind'),
		unbind = $method('unbind');
	/**
	 * @class jQuery.Model
	 * @tag core
	 * @download  http://jmvcsite.heroku.com/pluginify?plugins[]=jquery/model/model.js
	 * @test jquery/model/qunit.html
	 * @plugin jquery/model
	 * 
	 * Models wrap an application's data layer.  In large applications, a model is critical for:
	 * 
	 *  - [jquery.model.encapsulate Encapsulating] services so controllers + views don't care where data comes from.
	 *    
	 *  - Providing helper functions that make manipulating and abstracting raw service data easier.
	 * 
	 * This is done in two ways:
	 * 
	 *  - Requesting data from and interacting with services
	 *  
	 *  - Converting or wrapping raw service data into a more useful form.
	 * 
	 * 
	 * ## Basic Use
	 * 
	 * The [jQuery.Model] class provides a basic skeleton to organize pieces of your application's data layer.
	 * First, consider doing Ajax <b>without</b> a model.  In our imaginary app, you:
	 * 
	 *  - retrieve a list of tasks</li>
	 *  - display the number of days remaining for each task
	 *  - mark tasks as complete after users click them
	 * 
	 * Let's see how that might look without a model:
	 * 
	 * @codestart
	 * $.Controller("Tasks",
	 * {
	 *   // get tasks when the page is ready 
	 *   init: function() {
	 *     $.get('/tasks.json', this.callback('gotTasks'), 'json')
	 *   },
	 *  |* 
	 *   * assume json is an array like [{name: "trash", due_date: 1247111409283}, ...]
	 *   *|
	 *  gotTasks: function( json ) { 
	 *     for(var i =0; i < json.length; i++){
	 *       var taskJson = json[i];
	 *       
	 *       //calculate time remaining
	 *       var remaininTime = new Date() - new Date(taskJson.due_date);
	 *       
	 *       //append some html
	 *       $("#tasks").append("&lt;div class='task' taskid='"+taskJson.id+"'>"+
	 *                           "&lt;label>"+taskJson.name+"&lt;/label>"+
	 *                           "Due Date = "+remaininTime+"&lt;/div>")
	 *     }
	 *   },
	 *   // when a task is complete, get the id, make a request, remove it
	 *   ".task click" : function( el ) {
	 *     $.post('/tasks/'+el.attr('data-taskid')+'.json',
	 *     	 {complete: true}, 
	 *       function(){
	 *         el.remove();
	 *       })
	 *   }
	 * })
	 * @codeend
	 * 
	 * This code might seem fine for right now, but what if:
	 * 
	 *  - The service changes?
	 *  - Other parts of the app want to calculate <code>remaininTime</code>?
	 *  - Other parts of the app want to get tasks?</li>
	 *  - The same task is represented multiple palces on the page?
	 * 
	 * The solution is of course a strong model layer.  Lets look at what a
	 * a good model does for a controller before we learn how to make one:
	 * 
	 * @codestart
	 * $.Controller("Tasks",
	 * {
	 *   init: function() {
	 *     Task.findAll({}, this.callback('tasks'));
	 *   },
	 *   list : function(todos){
	 *     this.element.html("tasks.ejs", todos );
	 *   },
	 *   ".task click" : function( el ) {
	 *     el.model().update({complete: true},function(){
	 *       el.remove();
	 *     });
	 *   }
	 * });
	 * @codeend
	 * 
	 * In tasks.ejs
	 * 
	 * @codestart html
	 * &lt;% for(var i =0; i &lt; tasks.length; i++){ %>
	 * &lt;div &lt;%= tasks[i] %>>
	 *    &lt;label>&lt;%= tasks[i].name %>&lt;/label>
	 *    &lt;%= tasks[i].<b>timeRemaining</b>() %>
	 * &lt;/div>
	 * &lt;% } %>
	 * @codeend
	 * 
	 * Isn't that better!  Granted, some of the improvement comes because we used a view, but we've
	 * also made our controller completely understandable.  Now lets take a look at the model:
	 * 
	 * @codestart
	 * $.Model("Task",
	 * {
	 *  findAll: "/tasks.json",
	 *  update: "/tasks/{id}.json"
	 * },
	 * {
	 *  timeRemaining: function() {
	 *   return new Date() - new Date(this.due_date)
	 *  }
	 * })
	 * @codeend
	 * 
	 * Much better!  Now you have a single place where you 
	 * can organize Ajax functionality and
	 * wrap the data that it returned.  Lets go through 
	 * each bolded item in the controller and view.
	 * 
	 * ### Task.findAll
	 * 
	 * The findAll function requests data from "/tasks.json".  When the data is returned, 
	 * it converted by the [jQuery.Model.static.models models] function before being 
	 * passed to the success callback.
	 * 
	 * ### el.model
	 * 
	 * [jQuery.fn.model] is a jQuery helper that returns a model instance from an element.  The 
	 * list.ejs template assings tasks to elements with the following line:
	 * 
	 * @codestart html
	 * &lt;div &lt;%= tasks[i] %>> ... &lt;/div>
	 * @codeend
	 * 
	 * ### timeRemaining
	 * 
	 * timeRemaining is an example of wrapping your model's raw data with more useful functionality.
	 * 
	 * ## Other Good Stuff
	 * 
	 * This is just a tiny taste of what models can do.  Check out these other features:
	 * 
	 * ### [jquery.model.encapsulate Encapsulation]
	 * 
	 * Learn how to connect to services.
	 * 
	 *     $.Model("Task",{
	 *       findAll : "/tasks.json",    
	 *       findOne : "/tasks/{id}.json", 
	 *       create : "/tasks.json",
	 *       update : "/tasks/{id}.json"
	 *     },{})
	 * 
	 * ### [jquery.model.typeconversion Type Conversion]
	 * 
	 * Convert data like "10-20-1982" into new Date(1982,9,20) auto-magically:
	 * 
	 *     $.Model("Task",{
	 *       attributes : {birthday : "date"}
	 *       convert : {
	 *         date : function(raw){ ... }
	 *       }
	 *     },{})
	 * 
	 * ### [jQuery.Model.List]
	 * 
	 * Learn how to handle multiple instances with ease.
	 * 
	 *     $.Model.List("Task.List",{
	 *       destroyAll : function(){
	 *         var ids = this.map(function(c){ return c.id });
	 *         $.post("/destroy",
	 *           ids,
	 *           this.callback('destroyed'),
	 *           'json')
	 *       },
	 *       destroyed : function(){
	 *         this.each(function(){ this.destroyed() });
	 *       }
	 *     });
	 *     
	 *     ".destroyAll click" : function(){
	 *       this.find('.destroy:checked')
	 *           .closest('.task')
	 *           .models()
	 *           .destroyAll();
	 *     }
	 * 
	 * ### [jquery.model.validations Validations]
	 * 
	 * Validate your model's attributes.
	 * 
	 *     $.Model("Contact",{
	 *     init : function(){
	 *         this.validate("birthday",function(){
	 *             if(this.birthday > new Date){
	 *                 return "your birthday needs to be in the past"
	 *             }
	 *         })
	 *     }
	 *     ,{});
	 *     
	 *     
	 */
		// methods that we'll weave into model if provided
		ajaxMethods = 
		/** 
	     * @Static
	     */
		{
		create: function(str  ) {
			/**
			 * @function create
			 * Create is used to create a model instance on the server.  By implementing 
			 * create along with the rest of the [jquery.model.services service api], your models provide an abstract
			 * API for services.  
			 * 
			 * Create is called by save to create a new instance.  If you want to be able to call save on an instance
			 * you have to implement create.
			 * 
			 * The easiest way to implement create is to just give it the url to post data to:
			 * 
			 *     $.Model("Recipe",{
			 *       create: "/recipes"
			 *     },{})
			 *     
			 * This lets you create a recipe like:
			 *  
			 *     new Recipe({name: "hot dog"}).save(function(){
			 *       this.name //this is the new recipe
			 *     }).save(callback)
			 *  
			 * You can also implement create by yourself.  You just need to call success back with
			 * an object that contains the id of the new instance and any other properties that should be
			 * set on the instance.
			 *  
			 * For example, the following code makes a request 
			 * to '/recipes.json?name=hot+dog' and gets back
			 * something that looks like:
			 *  
			 *     { 
			 *       id: 5,
			 *       createdAt: 2234234329
			 *     }
			 * 
			 * The code looks like:
			 * 
			 *     $.Model("Recipe", {
			 *       create : function(attrs, success, error){
			 *         $.post("/recipes.json",attrs, success,"json");
			 *       }
			 *     },{})
			 * 
			 * ## API
			 * 
			 * @param {Object} attrs Attributes on the model instance
			 * @param {Function} success(attrs) the callback function, it must be called with an object 
			 * that has the id of the new instance and any other attributes the service needs to add.
			 * @param {Function} error a function to callback if something goes wrong.  
			 */
			return function(attrs, success, error){
				return ajax(str, attrs, success, error, "-restCreate")
			};
		},
		update: function( str ) {
			/**
			 * @function update
			 * Update is used to update a model instance on the server.  By implementing 
			 * update along with the rest of the [jquery.model.services service api], your models provide an abstract
			 * API for services.  
			 * 
			 * Update is called by [jQuery.Model.prototype.save] or [jQuery.Model.prototype.update] 
			 * on an existing model instance.  If you want to be able to call save on an instance
			 * you have to implement update.
			 * 
			 * The easist way to implement update is to just give it the url to put data to:
			 * 
			 *     $.Model("Recipe",{
			 *       create: "/recipes/{id}"
			 *     },{})
			 *     
			 * This lets you update a recipe like:
			 *  
			 *     // PUT /recipes/5 {name: "Hot Dog"}
			 *     recipe.update({name: "Hot Dog"},
			 *       function(){
			 *         this.name //this is the updated recipe
			 *       })
			 *  
			 * If your server doesn't use PUT, you can change it to post like:
			 * 
			 *     $.Model("Recipe",{
			 *       create: "POST /recipes/{id}"
			 *     },{})
			 * 
			 * Your server should send back an object with any new attributes the model 
			 * should have.  For example if your server udpates the "updatedAt" property, it
			 * should send back something like:
			 * 
			 *     // PUT /recipes/4 {name: "Food"} ->
			 *     {
			 *       updatedAt : "10-20-2011"
			 *     }
			 * 
			 * You can also implement create by yourself.  You just need to call success back with
			 * an object that contains any properties that should be
			 * set on the instance.
			 *  
			 * For example, the following code makes a request 
			 * to '/recipes/5.json?name=hot+dog' and gets back
			 * something that looks like:
			 *  
			 *     { 
			 *       updatedAt: "10-20-2011"
			 *     }
			 * 
			 * The code looks like:
			 * 
			 *     $.Model("Recipe", {
			 *       update : function(id, attrs, success, error){
			 *         $.post("/recipes/"+id+".json",attrs, success,"json");
			 *       }
			 *     },{})
			 * 
			 * ## API
			 * 
			 * @param {String} id the id of the model instance
			 * @param {Object} attrs Attributes on the model instance
			 * @param {Function} success(attrs) the callback function, it must be called with an object 
			 * that has the id of the new instance and any other attributes the service needs to add.
			 * @param {Function} error a function to callback if something goes wrong.  
			 */
			return function(id, attrs, success, error){
				return ajax(str, addId.call(this,attrs, id), success, error, "-restUpdate","put")
			}
		},
		destroy: function( str ) {
			/**
			 * @function destroy
			 * Destroy is used to remove a model instance from the server. By implementing 
			 * destroy along with the rest of the [jquery.model.services service api], your models provide an abstract
			 * service API.
			 * 
			 * You can implement destroy with a string like:
			 * 
			 *     $.Model("Thing",{
			 *       destroy : "POST /thing/destroy/{id}"
			 *     })
			 * 
			 * Or you can implement destroy manually like:
			 * 
			 *     $.Model("Thing",{
			 *       destroy : function(id, success, error){
			 *         $.post("/thing/destroy/"+id,{}, success);
			 *       }
			 *     })
			 * 
			 * You just have to call success if the destroy was successful.
			 * 
			 * @param {String|Number} id the id of the instance you want destroyed
			 * @param {Function} success the callback function, it must be called with an object 
			 * that has the id of the new instance and any other attributes the service needs to add.
			 * @param {Function} error a function to callback if something goes wrong.  
			 */
			return function( id, success, error ) {
				var attrs = {};
				attrs[this.id] = id;
				return ajax(str, attrs, success, error, "-restDestroy","delete")
			}
		},
		
		findAll: function( str ) {
			/**
			 * @function findAll
			 * FindAll is used to retrive a model instances from the server. By implementing 
			 * findAll along with the rest of the [jquery.model.services service api], your models provide an abstract
			 * service API.
			 * findAll returns a deferred ($.Deferred)
			 * 
			 * You can implement findAll with a string:
			 * 
			 *     $.Model("Thing",{
			 *       findAll : "/things.json"
			 *     },{})
			 * 
			 * Or you can implement it yourself.  The 'dataType' attribute is used to convert a JSON array of attributes
			 * to an array of instances.  For example:
			 * 
			 *     $.Model("Thing",{
			 *       findAll : function(params, success, error){
			 *         return $.ajax({
			 *         	 url: '/things.json',
			 *           type: 'get',
			 *           dataType: 'json thing.models',
			 *           data: params,
			 *           success: success,
			 *           error: error})
			 *       }
			 *     },{})
			 * 
			 * ## API
			 * 
			 * @param {Object} params data to refine the results.  An example might be passing {limit : 20} to
			 * limit the number of items retrieved.
			 * @param {Function} success(items) called with an array (or Model.List) of model instances.
			 * @param {Function} error
			 */
			return function(params, success, error){
				return ajax(str || this.shortName+"s.json", 
					params, 
					success, 
					error, 
					fixture.call(this,"s"),
					"get",
					"json "+this._shortName+".models");
			};
		},
		findOne: function( str ) {
			/**
			 * @function findOne
			 * FindOne is used to retrive a model instances from the server. By implementing 
			 * findOne along with the rest of the [jquery.model.services service api], your models provide an abstract
			 * service API.
			 * 
			 * You can implement findOne with a string:
			 * 
			 *     $.Model("Thing",{
			 *       findOne : "/things/{id}.json"
			 *     },{})
			 * 
			 * Or you can implement it yourself. 
			 * 
			 *     $.Model("Thing",{
			 *       findOne : function(params, success, error){
			 *         var self = this,
			 *             id = params.id;
			 *         delete params.id;
			 *         return $.get("/things/"+id+".json",
			 *           params,
			 *           success,
			 *           "json thing.model")
			 *       }
			 *     },{})
			 * 
			 * ## API
			 * 
			 * @param {Object} params data to refine the results. This is often something like {id: 5}.
			 * @param {Function} success(item) called with a model instance
			 * @param {Function} error
			 */
			return function(params, success, error){
				return ajax(str,
					params, 
					success,
					error, 
					fixture.call(this),
					"get",
					"json "+this._shortName+".model");
			};
		}
	};





	jQuery.Class("jQuery.Model",	{
		setup: function( superClass , stat, proto) {
			//we do not inherit attributes (or associations)
			var self=this;
			each(["attributes","associations","validations"],function(i,name){
				if (!self[name] || superClass[name] === self[name] ) {
					self[name] = {};
				}
			})

			//add missing converters
			if ( superClass.convert != this.convert ) {
				this.convert = extend(superClass.convert, this.convert);
			}


			this._fullName = underscore(this.fullName.replace(/\./g, "_"));
			this._shortName = underscore(this.shortName);

			if ( this.fullName.substr(0, 7) == "jQuery." ) {
				return;
			}

			//add this to the collection of models
			//jQuery.Model.models[this._fullName] = this;

			if ( this.listType ) {
				this.list = new this.listType([]);
			}
			
			for(var name in ajaxMethods){
				if(typeof this[name] !== 'function'){
					this[name] = ajaxMethods[name](this[name]);
				}
			}
			
			//add ajax converters
			var converters = {},
				convertName = "* "+this._shortName+".model";
			converters[convertName+"s"] = this.callback('models');
			converters[convertName] = this.callback('model');
			$.ajaxSetup({
				converters : converters
			});				
		},
		/**
		 * @attribute attributes
		 * Attributes contains a list of properties and their types
		 * for this model.  You can use this in conjunction with 
		 * [jQuery.Model.static.convert] to provide automatic 
		 * [jquery.model.typeconversion type conversion].  
		 * 
		 * The following converts dueDates to JavaScript dates:
		 * 
		 * @codestart
		 * $.Model("Contact",{
		 *   attributes : { 
		 *     birthday : 'date'
		 *   },
		 *   convert : {
		 *     date : function(raw){
		 *       if(typeof raw == 'string'){
		 *         var matches = raw.match(/(\d+)-(\d+)-(\d+)/)
		 *         return new Date( matches[1], 
		 *                  (+matches[2])-1, 
		 *                 matches[3] )
		 *       }else if(raw instanceof Date){
		 *           return raw;
		 *       }
		 *     }
		 *   }
		 * },{})
		 * @codeend
		 */
		attributes: {},
		/**
		 * @function wrap
		 * @hide
		 * @tag deprecated
		 * __warning__ : wrap is deprecated in favor of [jQuery.Model.static.model].  They 
		 * provide the same functionality; however, model works better with Deferreds.
		 * 
		 * Wrap is used to create a new instance from data returned from the server.
		 * It is very similar to doing <code> new Model(attributes) </code> 
		 * except that wrap will check if the data passed has an
		 * 
		 * - attributes,
		 * - data, or
		 * - <i>singularName</i>
		 * 
		 * property.  If it does, it will use that objects attributes.
		 * 
		 * Wrap is really a convience method for servers that don't return just attributes.
		 * 
		 * @param {Object} attributes
		 * @return {Model} an instance of the model
		 */
		// wrap place holder
		/**
		 * $.Model.model is used as a [http://api.jquery.com/extending-ajax/#Converters Ajax converter] 
		 * to convert the response of a [jQuery.Model.static.findOne] request 
		 * into a model instance.  
		 * 
		 * You will never call this method directly.  Instead, you tell $.ajax about it in findOne:
		 * 
		 *     $.Model('Recipe',{
		 *       findOne : function(params, success, error ){
		 *         return $.ajax({
		 *           url: '/services/recipes/'+params.id+'.json',
		 *           type: 'get',
		 *           
		 *           dataType : 'json recipe.model' //LOOK HERE!
		 *         });
		 *       }
		 *     },{})
		 * 
		 * This makes the result of findOne a [http://api.jquery.com/category/deferred-object/ $.Deferred]
		 * that resolves to a model instance:
		 * 
		 *     var deferredRecipe = Recipe.findOne({id: 6});
		 *     
		 *     deferredRecipe.then(function(recipe){
		 *       console.log('I am '+recipes.description+'.');
		 *     })
		 * 
		 * ## Non-standard Services
		 * 
		 * $.jQuery.model expects data to be name-value pairs like:
		 * 
		 *     {id: 1, name : "justin"}
		 *     
		 * It can also take an object with attributes in a data, attributes, or
		 * 'shortName' property.  For a App.Models.Person model the following will  all work:
		 * 
		 *     { data : {id: 1, name : "justin"} }
		 *     
		 *     { attributes : {id: 1, name : "justin"} }
		 *     
		 *     { person : {id: 1, name : "justin"} }
		 * 
		 * 
		 * ### Overwriting Model
		 * 
		 * If your service returns data like:
		 * 
		 *     {id : 1, name: "justin", data: {foo : "bar"} }
		 *     
		 * This will confuse $.Model.model.  You will want to overwrite it to create 
		 * an instance manually:
		 * 
		 *     $.Model('Person',{
		 *       model : function(data){
		 *         return new this(data);
		 *       }
		 *     },{})
		 *     
		 * ## API
		 * 
		 * @param {Object} attributes An object of name-value pairs or an object that has a 
		 *  data, attributes, or 'shortName' property that maps to an object of name-value pairs.
		 * @return {Model} an instance of the model
		 */
		model: function( attributes ) {
			if (!attributes ) {
				return null;
			}
			return new this(
				// checks for properties in an object (like rails 2.0 gives);
				isObject(attributes[this._shortName]) ||
				isObject(attributes.data) || 
				isObject(attributes.attributes) || 
				attributes);
		},
		/**
		 * @function wrapMany
		 * @hide
		 * @tag deprecated
		 * 
		 * __warning__ : wrapMany is deprecated in favor of [jQuery.Model.static.models].  They 
		 * provide the same functionality; however, models works better with Deferreds.
		 * 
		 * $.Model.wrapMany converts a raw array of JavaScript Objects into an array (or [jQuery.Model.List $.Model.List]) of model instances.
		 * 
		 *     // a Recipe Model wi
		 *     $.Model("Recipe",{
		 *       squareId : function(){
		 *         return this.id*this.id;
		 *       }
		 *     })
		 * 
		 *     var recipes = Recipe.wrapMany([{id: 1},{id: 2}])
		 *     recipes[0].squareId() //-> 1
		 * 
		 * If an array is not passed to wrapMany, it will look in the object's .data
		 * property.  
		 * 
		 * For example:
		 * 
		 *     var recipes = Recipe.wrapMany({data: [{id: 1},{id: 2}]})
		 *     recipes[0].squareId() //-> 1
		 * 
		 * 
		 * Often wrapMany is used with this.callback inside a model's [jQuery.Model.static.findAll findAll]
		 * method like:
		 * 
		 *     findAll : function(params, success, error){
		 *       $.get('/url',
		 *             params,
		 *             this.callback(['wrapMany',success]), 'json' )
		 *     }
		 * 
		 * If you are having problems getting your model to callback success correctly,
		 * make sure a request is being made (with firebug's net tab).  Also, you 
		 * might not use this.callback and instead do:
		 * 
		 *     findAll : function(params, success, error){
		 *       self = this;
		 *       $.get('/url',
		 *             params,
		 *             function(data){
		 *               var wrapped = self.wrapMany(data);
		 *               success(wrapped)
		 *             },
		 *             'json')
		 *     }
		 * 
		 * ## API
		 * 
		 * @param {Array} instancesRawData an array of raw name - value pairs like
		 * 
		 *     [{name: "foo", id: 4},{name: "bar", id: 5}]
		 *     
		 * @return {Array} a JavaScript array of instances or a [jQuery.Model.List list] of instances
		 *  if the model list plugin has been included.
		 */
		// wrapMany placeholder
		/**
		 * $.Model.models is used as a [http://api.jquery.com/extending-ajax/#Converters Ajax converter] 
		 * to convert the response of a [jQuery.Model.static.findAll] request 
		 * into an array (or [jQuery.Model.List $.Model.List]) of model instances.  
		 * 
		 * You will never call this method directly.  Instead, you tell $.ajax about it in findAll:
		 * 
		 *     $.Model('Recipe',{
		 *       findAll : function(params, success, error ){
		 *         return $.ajax({
		 *           url: '/services/recipes.json',
		 *           type: 'get',
		 *           data: params
		 *           
		 *           dataType : 'json recipe.models' //LOOK HERE!
		 *         });
		 *       }
		 *     },{})
		 * 
		 * This makes the result of findAll a [http://api.jquery.com/category/deferred-object/ $.Deferred]
		 * that resolves to a list of model instances:
		 * 
		 *     var deferredRecipes = Recipe.findAll({});
		 *     
		 *     deferredRecipes.then(function(recipes){
		 *       console.log('I have '+recipes.length+'recipes.');
		 *     })
		 * 
		 * ## Non-standard Services
		 * 
		 * $.jQuery.models expects data to be an array of name-value pairs like:
		 * 
		 *     [{id: 1, name : "justin"},{id:2, name: "brian"}, ...]
		 *     
		 * It can also take an object with additional data about the array like:
		 * 
		 *     {
		 *       count: 15000 //how many total items there might be
		 *       data: [{id: 1, name : "justin"},{id:2, name: "brian"}, ...]
		 *     }
		 * 
		 * In this case, models will return an array of instances found in 
		 * data, but with additional properties as expandos on the array:
		 * 
		 *     var people = Person.models({
		 *       count : 1500,
		 *       data : [{id: 1, name: 'justin'}, ...]
		 *     })
		 *     people[0].name // -> justin
		 *     people.count // -> 1500
		 * 
		 * ### Overwriting Models
		 * 
		 * If your service returns data like:
		 * 
		 *     {ballers: [{name: "justin", id: 5}]}
		 * 
		 * You will want to overwrite models to pass the base models what it expects like:
		 * 
		 *     $.Model('Person',{
		 *       models : function(data){
		 *         this._super(data.ballers);
		 *       }
		 *     },{})
		 * 
		 * @param {Array} instancesRawData an array of raw name - value pairs.
		 * @return {Array} a JavaScript array of instances or a [jQuery.Model.List list] of instances
		 *  if the model list plugin has been included.
		 */
		models: function( instancesRawData ) {
			if (!instancesRawData ) {
				return null;
			}
			var res = getList(this.List),
				arr = isArray(instancesRawData),
				raw = arr ? instancesRawData : instancesRawData.data,
				length = raw.length,
				i = 0;
			
			res._use_call = true; //so we don't call next function with all of these
			for (; i < length; i++ ) {
				res.push(this.model(raw[i]));
			}
			if (!arr ) { //push other stuff onto array
				for ( var prop in instancesRawData ) {
					if ( prop !== 'data' ) {
						res[prop] = instancesRawData[prop];
					}

				}
			}
			return res;
		},
		/**
		 * The name of the id field.  Defaults to 'id'. Change this if it is something different.
		 * 
		 * For example, it's common in .NET to use Id.  Your model might look like:
		 * 
		 * @codestart
		 * $.Model("Friends",{
		 *   id: "Id"
		 * },{});
		 * @codeend
		 */
		id: 'id',
		//if null, maybe treat as an array?
		/**
		 * Adds an attribute to the list of attributes for this class.
		 * @hide
		 * @param {String} property
		 * @param {String} type
		 */
		addAttr: function( property, type ) {
			var stub;

			if ( this.associations[property] ) {
				return;
			}
			
			stub = this.attributes[property] || (this.attributes[property] = type);
			return type;
		},
		// a collection of all models
		_models: {},
		/**
		 * If OpenAjax is available,
		 * publishes to OpenAjax.hub.  Always adds the shortName.event.
		 * 
		 * @codestart
		 * // publishes contact.completed
		 * Namespace.Contact.publish("completed",contact);
		 * @codeend
		 * 
		 * @param {String} event The event name to publish
		 * @param {Object} data The data to publish
		 */
		publish: function( event, data ) {
			
			if ( window.OpenAjax ) {
				OpenAjax.hub.publish(this._shortName + "." + event, data);
			}

		},
		guessType : function(){
			return "string"
		},
		/**
		 * @attribute convert
		 * @type Object
		 * An object of name-function pairs that are used to convert attributes.
		 * Check out [jQuery.Model.static.attributes] or 
		 * [jquery.model.typeconversion type conversion]
		 * for examples.
		 */
		convert: {
			"date": function( str ) {
				return typeof str === "string" ? (isNaN(Date.parse(str)) ? null : Date.parse(str)) : str;
			},
			"number": function( val ) {
				return parseFloat(val);
			},
			"boolean": function( val ) {
				return Boolean(val);
			}
		},
		bind: bind,
		unbind: unbind
	},
	/**
	 * @Prototype
	 */
	{
		/**
		 * Setup is called when a new model instance is created.
		 * It adds default attributes, then whatever attributes
		 * are passed to the class.
		 * Setup should never be called directly.
		 * 
		 * @codestart
		 * $.Model("Recipe")
		 * var recipe = new Recipe({foo: "bar"});
		 * recipe.foo //-> "bar"
		 * recipe.attr("foo") //-> "bar"
		 * @codeend
		 * 
		 * @param {Object} attributes a hash of attributes
		 */
		setup: function( attributes ) {
			// so we know not to fire events
			this._init = true;
			this.attrs(extend({},this.Class.defaults,attributes));
			delete this._init;
		},
		/**
		 * Sets the attributes on this instance and calls save.
		 * The instance needs to have an id.  It will use
		 * the instance class's [jQuery.Model.static.update update]
		 * method.
		 * 
		 * @codestart
		 * recipe.update({name: "chicken"}, success, error);
		 * @codeend
		 * 
		 * If OpenAjax.hub is available, the model will also
		 * publish a "<i>modelName</i>.updated" message with
		 * the updated instance.
		 * 
		 * @param {Object} attrs the model's attributes
		 * @param {Function} success called if a successful update
		 * @param {Function} error called if there's an error
		 */
		update: function( attrs, success, error ) {
			this.attrs(attrs);
			return this.save(success, error); //on success, we should 
		},
		/**
		 * Runs the validations on this model.  You can
		 * also pass it an array of attributes to run only those attributes.
		 * It returns nothing if there are no errors, or an object
		 * of errors by attribute.
		 * 
		 * To use validations, it's suggested you use the 
		 * model/validations plugin.
		 * 
		 * @codestart
		 * $.Model("Task",{
		 *   init : function(){
		 *     this.validatePresenceOf("dueDate")
		 *   }
		 * },{});
		 * 
		 * var task = new Task(),
		 *     errors = task.errors()
		 * 
		 * errors.dueDate[0] //-> "can't be empty"
		 * @codeend
		 */
		errors: function( attrs ) {
			if ( attrs ) {
				attrs = isArray(attrs) ? attrs : makeArray(arguments);
			}
			var errors = {},
				self = this,
				addErrors = function( attr, funcs ) {
					each(funcs, function( i, func ) {
						var res = func.call(self);
						if ( res ) {
							if (!errors.hasOwnProperty(attr) ) {
								errors[attr] = [];
							}

							errors[attr].push(res);
						}

					});
				};

			each(attrs || this.Class.validations || {}, function( attr, funcs ) {
				if ( typeof attr == 'number' ) {
					attr = funcs;
					funcs = self.Class.validations[attr];
				}
				addErrors(attr, funcs || []);
			});

			for ( var attr in errors ) {
				if ( errors.hasOwnProperty(attr) ) {
					return errors;
				}
			}
			return null;
		},
		/**
		 * Gets or sets an attribute on the model using setters and 
		 * getters if available.
		 * 
		 * @codestart
		 * $.Model("Recipe")
		 * var recipe = new Recipe();
		 * recipe.attr("foo","bar")
		 * recipe.foo //-> "bar"
		 * recipe.attr("foo") //-> "bar"
		 * @codeend
		 * 
		 * ## Setters
		 * 
		 * If you add a set<i>AttributeName</i> method on your model,
		 * it will be used to set the value.  The set method is called
		 * with the value and is expected to return the converted value.
		 * 
		 * @codestart
		 * $.Model("Recipe",{
		 *   setCreatedAt : function(raw){
		 *     return Date.parse(raw)
		 *   }
		 * })
		 * var recipe = new Recipe();
		 * recipe.attr("createdAt","Dec 25, 1995")
		 * recipe.createAt //-> Date
		 * @codeend
		 * 
		 * ## Asynchronous Setters
		 * 
		 * Sometimes, you want to perform an ajax request when 
		 * you set a property.  You can do this with setters too.
		 * 
		 * To do this, your setter should return undefined and
		 * call success with the converted value.  For example:
		 * 
		 * @codestart
		 * $.Model("Recipe",{
		 *   setTitle : function(title, success, error){
		 *     $.post(
		 *       "recipe/update/"+this.id+"/title",
		 *       title,
		 *       function(){
		 *         success(title);
		 *       },
		 *       "json")
		 *   }
		 * })
		 * 
		 * recipe.attr("title","fish")
		 * @codeend
		 * 
		 * ## Events
		 * 
		 * When you use attr, it can also trigger events.  This is
		 * covered in [jQuery.Model.prototype.bind].
		 * 
		 * @param {String} attribute the attribute you want to set or get
		 * @param {String|Number|Boolean} [value] value the value you want to set.
		 * @param {Function} [success] an optional success callback.  
		 *    This gets called if the attribute was successful.
		 * @param {Function} [error] an optional success callback.  
		 *    The error function is called with validation errors.
		 */
		attr: function( attribute, value, success, error ) {
			var cap = classize(attribute),
				get = "get" + cap;
			if ( value !== undefined ) {
				this._setProperty(attribute, value, success, error, cap);
				return this;
			}
			return this[get] ? this[get]() : this[attribute];
		},
		/**
		 * Binds to events on this model instance.  Typically 
		 * you'll bind to an attribute name.  Handler will be called
		 * every time the attribute value changes.  For example:
		 * 
		 * @codestart
		 * $.Model("School")
		 * var school = new School();
		 * school.bind("address", function(ev, address){
		 *   alert('address changed to '+address);
		 * })
		 * school.attr("address","1124 Park St");
		 * @codeend
		 * 
		 * You can also bind to attribute errors.
		 * 
		 * @codestart
		 * $.Model("School",{
		 *   setName : function(name, success, error){
		 *     if(!name){
		 *        error("no name");
		 *     }
		 *     return error;
		 *   }
		 * })
		 * var school = new School();
		 * school.bind("error.name", function(ev, mess){
		 *    mess // -> "no name";
		 * })
		 * school.attr("name","");
		 * @codeend
		 * 
		 * You can also bind to created, updated, and destroyed events.
		 * 
		 * @param {String} eventType the name of the event.
		 * @param {Function} handler a function to call back when an event happens on this model.
		 * @return {model} the model instance for chaining
		 */
		bind: bind,
		/**
		 * Unbinds an event handler from this instance.
		 * Read [jQuery.Model.prototype.bind] for 
		 * more information.
		 * @param {String} eventType
		 * @param {Function} handler
		 */
		unbind: unbind,
		/**
		 * Checks if there is a set_<i>property</i> value.  If it returns true, lets it handle; otherwise
		 * saves it.
		 * @hide
		 * @param {Object} property
		 * @param {Object} value
		 */
		_setProperty: function( property, value, success, error, capitalized ) {
			// the potential setter name
			var setName = "set" + capitalized,
				//the old value
				old = this[property],
				self = this,
				errorCallback = function( errors ) {
					var stub;
					stub = error && error.call(self, errors);
					$(self).triggerHandler("error." + property, errors);
				};

			// provides getter / setters
			// 
			if ( this[setName] && 
				(value = this[setName](value, this.callback('_updateProperty', property, value, old, success, errorCallback), errorCallback)) === undefined ) {
				return;
			}
			this._updateProperty(property, value, old, success, errorCallback);
		},
		/**
		 * Triggers events when a property has been updated
		 * @hide
		 * @param {Object} property
		 * @param {Object} value
		 * @param {Object} old
		 * @param {Object} success
		 */
		_updateProperty: function( property, value, old, success, errorCallback ) {
			var Class = this.Class,
				val, type = Class.attributes[property] || Class.addAttr(property, Class.guessType(value)),
				//the converter
				converter = Class.convert[type],
				errors = null,
				stub;

			val = this[property] = (value === null ? //if the value is null or undefined
			null : // it should be null
			(converter ? converter.call(Class, value) : //convert it to something useful
			value)); //just return it
			//validate (only if not initializing, this is for performance)
			if (!this._init ) {
				errors = this.errors(property);
			}

			if ( errors ) {
				//get an array of errors
				errorCallback(errors);
			} else {
				if ( old !== val && !this._init ) {
					$(this).triggerHandler(property, [val]);
					$(this).triggerHandler("updated.attr", [property,val, old]); // this is for 3.1
				}
				stub = success && success(this);

			}

			//if this class has a global list, add / remove from the list.
			if ( property === Class.id && val !== null && Class.list ) {
				// if we didn't have an old id, add ourselves
				if (!old ) {
					Class.list.push(this);
				} else if ( old != val ) {
					// if our id has changed ... well this should be ok
					Class.list.remove(old);
					Class.list.push(this);
				}
			}

		},
		/**
		 * Gets or sets a list of attributes. 
		 * Each attribute is set with [jQuery.Model.prototype.attr attr].
		 * 
		 * @codestart
		 * recipe.attrs({
		 *   name: "ice water",
		 *   instructions : "put water in a glass"
		 * })
		 * @codeend
		 * 
		 * This can be used nicely with [jquery.model.events].
		 * 
		 * @param {Object} [attributes]  if present, the list of attributes to send
		 * @return {Object} the current attributes of the model
		 */
		attrs: function( attributes ) {
			var key;
			if (!attributes ) {
				attributes = {};
				for ( key in this.Class.attributes ) {
					if ( this.Class.attributes.hasOwnProperty(key) ) {
						attributes[key] = this.attr(key);
					}
				}
			} else {
				var idName = this.Class.id;
				//always set the id last
				for ( key in attributes ) {
					if ( key != idName ) {
						this.attr(key, attributes[key]);
					}
				}
				if ( idName in attributes ) {
					this.attr(idName, attributes[idName]);
				}

			}
			return attributes;
		},
		/**
		 * Returns if the instance is a new object.  This is essentially if the
		 * id is null or undefined.
		 * 
		 *     new Recipe({id: 1}).isNew() //-> false
		 * @return {Boolean} false if an id is set, true if otherwise.
		 */
		isNew: function() {
			var id = getId(this);
			return (id === undefined || id === null); //if null or undefined
		},
		/**
		 * Saves the instance if there are no errors.  
		 * If the instance is new, [jQuery.Model.static.create] is
		 * called; otherwise, [jQuery.Model.static.update] is
		 * called.
		 * 
		 * @codestart
		 * recipe.save(success, error);
		 * @codeend
		 * 
		 * If OpenAjax.hub is available, after a successful create or update, 
		 * "<i>modelName</i>.created" or "<i>modelName</i>.updated" is published.
		 * 
		 * @param {Function} [success] called if a successful save.
		 * @param {Function} [error] called if the save was not successful.
		 */
		save: function( success, error ) {
			return makeRequest(this, this.isNew()  ? 'create' : 'update' , success, error);
		},

		/**
		 * Destroys the instance by calling 
		 * [jQuery.Model.static.destroy] with the id of the instance.
		 * 
		 * @codestart
		 * recipe.destroy(success, error);
		 * @codeend
		 * 
		 * If OpenAjax.hub is available, after a successful
		 * destroy "<i>modelName</i>.destroyed" is published
		 * with the model instance.
		 * 
		 * @param {Function} [success] called if a successful destroy
		 * @param {Function} [error] called if an unsuccessful destroy
		 */
		destroy: function( success, error ) {
			return makeRequest(this, 'destroy' , success, error , 'destroyed');
		},
		

		/**
		 * Returns a unique identifier for the model instance.  For example:
		 * @codestart
		 * new Todo({id: 5}).identity() //-> 'todo_5'
		 * @codeend
		 * Typically this is used in an element's shortName property so you can find all elements
		 * for a model with [jQuery.Model.prototype.elements elements].
		 * @return {String}
		 */
		identity: function() {
			var id = getId(this);
			return this.Class._fullName + '_' + (this.Class.escapeIdentity ? encodeURIComponent(id) : id);
		},
		/**
		 * Returns elements that represent this model instance.  For this to work, your element's should
		 * us the [jQuery.Model.prototype.identity identity] function in their class name.  Example:
		 * 
		 *     <div class='todo <%= todo.identity() %>'> ... </div>
		 * 
		 * This also works if you hooked up the model:
		 * 
		 *     <div <%= todo %>> ... </div>
		 *     
		 * Typically, you'll use this as a response of an OpenAjax message:
		 * 
		 *     "todo.destroyed subscribe": function(called, todo){
		 *       todo.elements(this.element).remove();
		 *     }
		 * 
		 * ## API
		 * 
		 * @param {String|jQuery|element} context If provided, only elements inside this element
		 * that represent this model will be returned.
		 * 
		 * @return {jQuery} Returns a jQuery wrapped nodelist of elements that have this model instances
		 *  identity in their class name.
		 */
		elements: function( context ) {
			return $("." + this.identity(), context);
		},
		/**
		 * Publishes to OpenAjax.hub
		 * 
		 *     $.Model('Task', {
		 *       complete : function(cb){
		 *         var self = this;
		 *         $.post('/task/'+this.id,
		 *           {complete : true},
		 *           function(){
		 *             self.attr('completed', true);
		 *             self.publish('completed');
		 *           })
		 *       }
		 *     })
		 *     
		 *     
		 * @param {String} event The event type.  The model's short name will be automatically prefixed.
		 * @param {Object} [data] if missing, uses the instance in {data: this}
		 */
		publish: function( event, data ) {
			this.Class.publish(event, data || this);
		},
		hookup: function( el ) {
			var shortName = this.Class._shortName,
				models = $.data(el, "models") || $.data(el, "models", {});
			$(el).addClass(shortName + " " + this.identity());
			models[shortName] = this;
		}
	});
	// map wrapMany
	$.Model.wrapMany = $.Model.models;
	$.Model.wrap = $.Model.model;


	each([
	/**
	 * @function created
	 * @hide
	 * Called by save after a new instance is created.  Publishes 'created'.
	 * @param {Object} attrs
	 */
	"created",
	/**
	 * @function updated
	 * @hide
	 * Called by save after an instance is updated.  Publishes 'updated'.
	 * @param {Object} attrs
	 */
	"updated",
	/**
	 * @function destroyed
	 * @hide
	 * Called after an instance is destroyed.  
	 *   - Publishes "shortName.destroyed".
	 *   - Triggers a "destroyed" event on this model.
	 *   - Removes the model from the global list if its used.
	 * 
	 */
	"destroyed"], function( i, funcName ) {
		$.Model.prototype[funcName] = function( attrs ) {
			var stub;

			if ( funcName === 'destroyed' && this.Class.list ) {
				this.Class.list.remove(getId(this));
			}
			stub = attrs && typeof attrs == 'object' && this.attrs(attrs.attrs ? attrs.attrs() : attrs);
			$(this).triggerHandler(funcName);
			this.publish(funcName, this);
			$([this.Class]).triggerHandler(funcName, this);
			return [this].concat(makeArray(arguments)); // return like this for this.callback chains
		};
	});

	/**
	 *  @add jQuery.fn
	 */
	// break
	/**
	 * @function models
	 * Returns a list of models.  If the models are of the same
	 * type, and have a [jQuery.Model.List], it will return 
	 * the models wrapped with the list.
	 * 
	 * @codestart
	 * $(".recipes").models() //-> [recipe, ...]
	 * @codeend
	 * 
	 * @param {jQuery.Class} [type] if present only returns models of the provided type.
	 * @return {Array|jQuery.Model.List} returns an array of model instances that are represented by the contained elements.
	 */
	$.fn.models = function( type ) {
		//get it from the data
		var collection = [],
			kind, ret, retType;
		this.each(function() {
			each($.data(this, "models") || {}, function( name, instance ) {
				//either null or the list type shared by all classes
				kind = kind === undefined ? 
					instance.Class.List || null : 
					(instance.Class.List === kind ? kind : null);
				collection.push(instance);
			});
		});

		ret = getList(kind);

		ret.push.apply(ret, unique(collection));
		return ret;
	};
	/**
	 * @function model
	 * 
	 * Returns the first model instance found from [jQuery.fn.models] or
	 * sets the model instance on an element.
	 * 
	 *     //gets an instance
	 *     ".edit click" : function(el) {
	 *       el.closest('.todo').model().destroy()
	 *     },
	 *     // sets an instance
	 *     list : function(items){
	 *        var el = this.element;
	 *        $.each(item, function(item){
	 *          $('<div/>').model(item)
	 *            .appendTo(el)
	 *        })
	 *     }
	 * 
	 * @param {Object} [type] The type of model to return.  If a model instance is provided
	 * it will add the model to the element.
	 */
	$.fn.model = function( type ) {
		if ( type && type instanceof $.Model ) {
			type.hookup(this[0]);
			return this;
		} else {
			return this.models.apply(this, arguments)[0];
		}

	};
	/**
	 * @page jquery.model.services Service APIs
	 * @parent jQuery.Model
	 * 
	 * Models provide an abstract API for connecting to your Services.  
	 * By implementing static:
	 * 
	 *  - [jQuery.Model.static.findAll] 
	 *  - [jQuery.Model.static.findOne] 
	 *  - [jQuery.Model.static.create] 
	 *  - [jQuery.Model.static.update] 
	 *  - [jQuery.Model.static.destroy]
	 *  
	 * You can find more details on how to implement each method.
	 * Typically, you can just use templated service urls. But if you need to
	 * implement these methods yourself, the following
	 * is a useful quick reference:
	 * 
	 * ### create(attrs, success([attrs]), error()) -> deferred
	 *  
	 *  - <code>attrs</code> - an Object of attribute / value pairs
	 *  - <code>success([attrs])</code> - Create calls success when the request has completed 
	 *    successfully.  Success can be called back with an object that represents
	 *    additional properties that will be set on the instance. For example, the server might 
	 *    send back an updatedAt date.
	 *  - <code>error</code> - Create should callback error if an error happens during the request
	 *  - <code>deferred</code> - A deferred that gets resolved to any additional attrs
	 *    that might need to be set on the model instance.
	 * 
	 * 
	 * ### findAll( params, success(items), error) -> deferred
	 * 
	 * 
	 *  - <code>params</code> - an Object that filters the items returned
	 *  - <code>success(items)</code> - success should be called with an Array of Model instances.
	 *  - <code>error</code> - called if an error happens during the request
	 *  - <code>deferred</code> - A deferred that gets resolved to the list of items
	 *          
	 * ### findOne(params, success(items), error) -> deferred
	 *          
	 *  - <code>params</code> - an Object that filters the item returned
	 *  - <code>success(item)</code> - success should be called with a model instance.
	 *  - <code>error</code> - called if an error happens during the request
	 *  - <code>deferred</code> - A deferred that gets resolved to a model instance
	 *        
	 * ### update(id, attrs, success([attrs]), error()) -> deferred
	 *  
	 *  - <code>id</code> - the id of the instance you are updating
	 *  - <code>attrs</code> - an Object of attribute / value pairs
	 *  - <code>success([attrs])</code> - Call success when the request has completed 
	 *    successfully.  Success can be called back with an object that represents
	 *    additional properties that will be set on the instance. For example, the server might 
	 *    send back an updatedAt date.
	 *  - <code>error</code> - Callback error if an error happens during the request
	 *  - <code>deferred</code> - A deferred that gets resolved to any additional attrs
	 *      that might need to be set on the model instance.
	 *     
	 * ### destroy(id, success([attrs]), error()) -> deferred
	 *  
	 *  - <code>id</code> - the id of the instance you are destroying
	 *  - <code>success([attrs])</code> - Calls success when the request has completed 
	 *      successfully.  Success can be called back with an object that represents
	 *      additional properties that will be set on the instance. 
	 *  - <code>error</code> - Create should callback error if an error happens during the request
	 *  - <code>deferred</code> - A deferred that gets resolved to any additional attrs
	 *      that might need to be set on the model instance.
	 */
})(jQuery);

//jquery.view.js

(function( $ ) {

	// converts to an ok dom id
	var toId = function( src ) {
		return src.replace(/^\/\//, "").replace(/[\/\.]/g, "_");
	},
		// used for hookup ids
		id = 1;

	/**
	 * @class jQuery.View
	 * @tag core
	 * @plugin jquery/view
	 * @test jquery/view/qunit.html
	 * @download dist/jquery.view.js
	 * 
	 * View provides a uniform interface for using templates with 
	 * jQuery. When template engines [jQuery.View.register register] 
	 * themselves, you are able to:
	 * 
	 *  - Use views with jQuery extensions [jQuery.fn.after after], [jQuery.fn.append append],
	 *   [jQuery.fn.before before], [jQuery.fn.html html], [jQuery.fn.prepend prepend],
	 *   [jQuery.fn.replaceWith replaceWith], [jQuery.fn.text text].
	 *  - Template loading from html elements and external files.
	 *  - Synchronous and asynchronous template loading.
	 *  - Deferred Rendering.
	 *  - Template caching.
	 *  - Bundling of processed templates in production builds.
	 *  - Hookup jquery plugins directly in the template.
	 *  
	 * ## Use
	 * 
	 * 
	 * When using views, you're almost always wanting to insert the results 
	 * of a rendered template into the page. jQuery.View overwrites the 
	 * jQuery modifiers so using a view is as easy as: 
	 * 
	 *     $("#foo").html('mytemplate.ejs',{message: 'hello world'})
	 *
	 * This code:
	 * 
	 *  - Loads the template a 'mytemplate.ejs'. It might look like:
	 *    <pre><code>&lt;h2>&lt;%= message %>&lt;/h2></pre></code>
	 *  
	 *  - Renders it with {message: 'hello world'}, resulting in:
	 *    <pre><code>&lt;div id='foo'>"&lt;h2>hello world&lt;/h2>&lt;/div></pre></code>
	 *  
	 *  - Inserts the result into the foo element. Foo might look like:
	 *    <pre><code>&lt;div id='foo'>&lt;h2>hello world&lt;/h2>&lt;/div></pre></code>
	 * 
	 * ## jQuery Modifiers
	 * 
	 * You can use a template with the following jQuery modifiers:
	 * 
	 * <table>
	 * <tr><td>[jQuery.fn.after after]</td><td> <code>$('#bar').after('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after append] </td><td>  <code>$('#bar').append('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after before] </td><td> <code>$('#bar').before('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after html] </td><td> <code>$('#bar').html('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after prepend] </td><td> <code>$('#bar').prepend('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after replaceWith] </td><td> <code>$('#bar').replaceWidth('temp.jaml',{});</code></td></tr>
	 * <tr><td>[jQuery.fn.after text] </td><td> <code>$('#bar').text('temp.jaml',{});</code></td></tr>
	 * </table>
	 * 
	 * You always have to pass a string and an object (or function) for the jQuery modifier 
	 * to user a template.
	 * 
	 * ## Template Locations
	 * 
	 * View can load from script tags or from files. 
	 * 
	 * ## From Script Tags
	 * 
	 * To load from a script tag, create a script tag with your template and an id like: 
	 * 
	 * <pre><code>&lt;script type='text/ejs' id='recipes'>
	 * &lt;% for(var i=0; i &lt; recipes.length; i++){ %>
	 *   &lt;li>&lt;%=recipes[i].name %>&lt;/li>
	 * &lt;%} %>
	 * &lt;/script></code></pre>
	 * 
	 * Render with this template like: 
	 * 
	 * @codestart
	 * $("#foo").html('recipes',recipeData)
	 * @codeend
	 * 
	 * Notice we passed the id of the element we want to render.
	 * 
	 * ## From File
	 * 
	 * You can pass the path of a template file location like:
	 * 
	 *     $("#foo").html('templates/recipes.ejs',recipeData)
	 * 
	 * However, you typically want to make the template work from whatever page they 
	 * are called from.  To do this, use // to look up templates from JMVC root:
	 * 
	 *     $("#foo").html('//app/views/recipes.ejs',recipeData)
	 *     
	 * Finally, the [jQuery.Controller.prototype.view controller/view] plugin can make looking
	 * up a thread (and adding helpers) even easier:
	 * 
	 *     $("#foo").html( this.view('recipes', recipeData) )
	 * 
	 * ## Packaging Templates
	 * 
	 * If you're making heavy use of templates, you want to organize 
	 * them in files so they can be reused between pages and applications.
	 * 
	 * But, this organization would come at a high price 
	 * if the browser has to 
	 * retrieve each template individually. The additional 
	 * HTTP requests would slow down your app. 
	 * 
	 * Fortunately, [steal.static.views steal.views] can build templates 
	 * into your production files. You just have to point to the view file like: 
	 * 
	 *     steal.views('path/to/the/view.ejs');
     *
	 * ## Asynchronous
	 * 
	 * By default, retrieving requests is done synchronously. This is 
	 * fine because StealJS packages view templates with your JS download. 
	 * 
	 * However, some people might not be using StealJS or want to delay loading 
	 * templates until necessary. If you have the need, you can 
	 * provide a callback paramter like: 
	 * 
	 *     $("#foo").html('recipes',recipeData, function(result){
	 *       this.fadeIn()
	 *     });
	 * 
	 * The callback function will be called with the result of the 
	 * rendered template and 'this' will be set to the original jQuery object.
	 * 
	 * ## Deferreds (3.0.6)
	 * 
	 * If you pass deferreds to $.View or any of the jQuery 
	 * modifiers, the view will wait until all deferreds resolve before 
	 * rendering the view.  This makes it a one-liner to make a request and 
	 * use the result to render a template. 
	 * 
	 * The following makes a request for todos in parallel with the 
	 * todos.ejs template.  Once todos and template have been loaded, it with
	 * render the view with the todos.
	 * 
	 *     $('#todos').html("todos.ejs",Todo.findAll());
	 * 
	 * ## Just Render Templates
	 * 
	 * Sometimes, you just want to get the result of a rendered 
	 * template without inserting it, you can do this with $.View: 
	 * 
	 *     var out = $.View('path/to/template.jaml',{});
	 *     
     * ## Preloading Templates
	 * 
	 * You can preload templates asynchronously like:
	 * 
	 *     $.get('path/to/template.jaml',{},function(){},'view');
	 * 
	 * ## Supported Template Engines
	 * 
	 * JavaScriptMVC comes with the following template languages:
	 * 
	 *   - EmbeddedJS
	 *     <pre><code>&lt;h2>&lt;%= message %>&lt;/h2></code></pre>
	 *     
	 *   - JAML
	 *     <pre><code>h2(data.message);</code></pre>
	 *     
	 *   - Micro
	 *     <pre><code>&lt;h2>{%= message %}&lt;/h2></code></pre>
	 *     
	 *   - jQuery.Tmpl
	 *     <pre><code>&lt;h2>${message}&lt;/h2></code></pre>

	 * 
	 * The popular <a href='http://awardwinningfjords.com/2010/08/09/mustache-for-javascriptmvc-3.html'>Mustache</a> 
	 * template engine is supported in a 2nd party plugin.
	 * 
	 * ## Using other Template Engines
	 * 
	 * It's easy to integrate your favorite template into $.View and Steal.  Read 
	 * how in [jQuery.View.register].
	 * 
	 * @constructor
	 * 
	 * Looks up a template, processes it, caches it, then renders the template
	 * with data and optional helpers.
	 * 
	 * With [stealjs StealJS], views are typically bundled in the production build.
	 * This makes it ok to use views synchronously like:
	 * 
	 * @codestart
	 * $.View("//myplugin/views/init.ejs",{message: "Hello World"})
	 * @codeend
	 * 
	 * If you aren't using StealJS, it's best to use views asynchronously like:
	 * 
	 * @codestart
	 * $.View("//myplugin/views/init.ejs",
	 *        {message: "Hello World"}, function(result){
	 *   // do something with result
	 * })
	 * @codeend
	 * 
	 * @param {String} view The url or id of an element to use as the template's source.
	 * @param {Object} data The data to be passed to the view.
	 * @param {Object} [helpers] Optional helper functions the view might use. Not all
	 * templates support helpers.
	 * @param {Object} [callback] Optional callback function.  If present, the template is 
	 * retrieved asynchronously.  This is a good idea if you aren't compressing the templates
	 * into your view.
	 * @return {String} The rendered result of the view or if deferreds are passed, a deferred that will contain
	 * the rendered result of the view.
	 */

	var $view, render, checkText, get, getRenderer
		isDeferred = function(obj){
			return obj && $.isFunction(obj.always) // check if obj is a $.Deferred
		},
		// gets an array of deferreds from an object
		// this only goes one level deep
		getDeferreds =  function(data){
			var deferreds = [];
		
			// pull out deferreds
			if(isDeferred(data)){
				return [data]
			}else{
				for(var prop in data) {
					if(isDeferred(data[prop])) {
						deferreds.push(data[prop]);
					}
				}
			}
			return deferreds;
		},
		// gets the useful part of deferred
		// this is for Models and $.ajax that give arrays
		usefulPart = function(resolved){
			return $.isArray(resolved) && 
					resolved.length ===3 && 
					resolved[1] === 'success' ?
						resolved[0] : resolved
		};

	$view = $.View = function( view, data, helpers, callback ) {
		if ( typeof helpers === 'function' ) {
			callback = helpers;
			helpers = undefined;
		}
		
		// see if we got passed any deferreds
		var deferreds = getDeferreds(data);
		
		
		if(deferreds.length) { // does data contain any deferreds?
			
			// the deferred that resolves into the rendered content ...
			var deferred = $.Deferred();
			
			// add the view request to the list of deferreds
			deferreds.push(get(view, true))
			
			// wait for the view and all deferreds to finish
			$.when.apply($, deferreds).then(function(resolved) {
				var objs = $.makeArray(arguments),
					renderer = objs.pop()[0],
					result; //get the view render function
				
				// make data look like the resolved deferreds
				if (isDeferred(data)) {
					data = usefulPart(resolved);
				}
				else {
					for (var prop in data) {
						if (isDeferred(data[prop])) {
							data[prop] = usefulPart(objs.shift());
						}
					}
				}
				result = renderer(data, helpers);
				
				//resolve with the rendered view
				deferred.resolve( result ); // this does not work as is...
				callback && callback(result);
			});
			// return the deferred ....
			return deferred.promise();
		}
		else {

			var response,
				async = typeof callback === "function",
				deferred = get(view, async);
			
			if(async){
				response = deferred;
				deferred.done(function(renderer){
					callback(renderer(data, helpers))
				})
			} else {
				deferred.done(function(renderer){
					response = renderer(data, helpers);
				});
			}
			
			return response;
		}
	};
	// makes sure there's a template
	checkText = function( text, url ) {
		if (!text.match(/[^\s]/) ) {
			
			throw "$.View ERROR: There is no template or an empty template at " + url;
		}
	};
	get = function(url , async){
		return $.ajax({
				url: url,
				dataType : "view",
				async : async
		});
	};
	
	// you can request a view renderer (a function you pass data to and get html)
	$.ajaxTransport("view", function(options, orig){
		var view = orig.url,
			suffix = view.match(/\.[\w\d]+$/),
			type, el, id, renderer, url = view,
			jqXHR,
			response = function(text){
				var func = type.renderer(id, text);
				if ( $view.cache ) {
					$view.cached[id] = func;
				}
				return {
					view: func
				};
			};
			
        // if we have an inline template, derive the suffix from the 'text/???' part
        // this only supports '<script></script>' tags
        if ( el = document.getElementById(view)) {
          suffix = el.type.match(/\/[\d\w]+$/)[0].replace(/^\//, '.');
        }
		
		//if there is no suffix, add one
		if (!suffix ) {
			suffix = $view.ext;
			url = url + $view.ext;
		}

		//convert to a unique and valid id
		id = toId(url);

		//if a absolute path, use steal to get it
		if ( url.match(/^\/\//) ) {
			if (typeof steal === "undefined") {
				url = "/"+url.substr(2);
			}
			else {
				url = steal.root.join(url.substr(2));
			}
		}

		//get the template engine
		type = $view.types[suffix];

		return {
			send : function(headers, callback){
				if($view.cached[id]){
					return callback( 200, "success", {view: $view.cached[id]} );
				} else if( el  ) {
					callback( 200, "success", response(el.innerHTML) );
				} else {
					jqXHR = $.ajax({
						async : orig.async,
						url: url,
						dataType: "text",
						error: function() {
							checkText("", url);
							callback(404);
						},
						success: function( text ) {
							checkText(text, url);
							callback(200, "success", response(text) )
						}
					});
				}
			},
			abort : function(){
				jqXHR && jqXHR.abort();
			}
		}
	})
	$.extend($view, {
		/**
		 * @attribute hookups
		 * @hide
		 * A list of pending 'hookups'
		 */
		hookups: {},
		/**
		 * @function hookup
		 * Registers a hookup function that can be called back after the html is 
		 * put on the page.  Typically this is handled by the template engine.  Currently
		 * only EJS supports this functionality.
		 * 
		 *     var id = $.View.hookup(function(el){
		 *            //do something with el
		 *         }),
		 *         html = "<div data-view-id='"+id+"'>"
		 *     $('.foo').html(html);
		 * 
		 * 
		 * @param {Function} cb a callback function to be called with the element
		 * @param {Number} the hookup number
		 */
		hookup: function( cb ) {
			var myid = ++id;
			$view.hookups[myid] = cb;
			return myid;
		},
		/**
		 * @attribute cached
		 * @hide
		 * Cached are put in this object
		 */
		cached: {},
		/**
		 * @attribute cache
		 * Should the views be cached or reloaded from the server. Defaults to true.
		 */
		cache: true,
		/**
		 * @function register
		 * Registers a template engine to be used with 
		 * view helpers and compression.  
		 * 
		 * ## Example
		 * 
		 * @codestart
		 * $.View.register({
		 * 	suffix : "tmpl",
		 * 	renderer: function( id, text ) {
		 * 		return function(data){
		 * 			return jQuery.render( text, data );
		 * 		}
		 * 	},
		 * 	script: function( id, text ) {
		 * 		var tmpl = $.tmpl(text).toString();
		 * 		return "function(data){return ("+
		 * 		  	tmpl+
		 * 			").call(jQuery, jQuery, data); }";
		 * 	}
		 * })
		 * @codeend
		 * Here's what each property does:
		 * 
 		 *    * suffix - files that use this suffix will be processed by this template engine
 		 *    * renderer - returns a function that will render the template provided by text
 		 *    * script - returns a string form of the processed template function.
		 * 
		 * @param {Object} info a object of method and properties 
		 * 
		 * that enable template integration:
		 * <ul>
		 *   <li>suffix - the view extension.  EX: 'ejs'</li>
		 *   <li>script(id, src) - a function that returns a string that when evaluated returns a function that can be 
		 *    used as the render (i.e. have func.call(data, data, helpers) called on it).</li>
		 *   <li>renderer(id, text) - a function that takes the id of the template and the text of the template and
		 *    returns a render function.</li>
		 * </ul>
		 */
		register: function( info ) {
			this.types["." + info.suffix] = info;
		},
		types: {},
		/**
		 * @attribute ext
		 * The default suffix to use if none is provided in the view's url.  
		 * This is set to .ejs by default.
		 */
		ext: ".ejs",
		/**
		 * Returns the text that 
		 * @hide 
		 * @param {Object} type
		 * @param {Object} id
		 * @param {Object} src
		 */
		registerScript: function( type, id, src ) {
			return "$.View.preload('" + id + "'," + $view.types["." + type].script(id, src) + ");";
		},
		/**
		 * @hide
		 * Called by a production script to pre-load a renderer function
		 * into the view cache.
		 * @param {String} id
		 * @param {Function} renderer
		 */
		preload: function( id, renderer ) {
			$view.cached[id] = function( data, helpers ) {
				return renderer.call(data, data, helpers);
			};
		}

	});


	//---- ADD jQUERY HELPERS -----
	//converts jquery functions to use views	
	var convert, modify, isTemplate, getCallback, hookupView, funcs;

	convert = function( func_name ) {
		var old = $.fn[func_name];

		$.fn[func_name] = function() {
			var args = $.makeArray(arguments),
				callbackNum, 
				callback, 
				self = this,
				result;

			//check if a template
			if ( isTemplate(args) ) {

				// if we should operate async
				if ((callbackNum = getCallback(args))) {
					callback = args[callbackNum];
					args[callbackNum] = function( result ) {
						modify.call(self, [result], old);
						callback.call(self, result);
					};
					$view.apply($view, args);
					return this;
				}
				result = $view.apply($view, args);
				if(!isDeferred( result ) ){
					args = [result];
				}else{
					result.done(function(res){
						modify.call(self, [res], old);
					})
					return this;
				}
				//otherwise do the template now
				
			}

			return modify.call(this, args, old);
		};
	};
	// modifies the html of the element
	modify = function( args, old ) {
		var res, stub, hooks;

		//check if there are new hookups
		for ( var hasHookups in $view.hookups ) {
			break;
		}

		//if there are hookups, get jQuery object
		if ( hasHookups ) {
			hooks = $view.hookups;
			$view.hookups = {};
			args[0] = $(args[0]);
		}
		res = old.apply(this, args);

		//now hookup hookups
		if ( hasHookups ) {
			hookupView(args[0], hooks);
		}
		return res;
	};

	// returns true or false if the args indicate a template is being used
	isTemplate = function( args ) {
		var secArgType = typeof args[1];

		return typeof args[0] == "string" && (secArgType == 'object' || secArgType == 'function') && !args[1].nodeType && !args[1].jquery;
	};

	//returns the callback if there is one (for async view use)
	getCallback = function( args ) {
		return typeof args[3] === 'function' ? 3 : typeof args[2] === 'function' && 2;
	};

	hookupView = function( els , hooks) {
		//remove all hookups
		var hookupEls, 
			len, i = 0,
			id, func;
		els = els.filter(function(){
			return this.nodeType != 3; //filter out text nodes
		})
		hookupEls = els.add("[data-view-id]", els);
		len = hookupEls.length;
		for (; i < len; i++ ) {
			if ( hookupEls[i].getAttribute && (id = hookupEls[i].getAttribute('data-view-id')) && (func = hooks[id]) ) {
				func(hookupEls[i], id);
				delete hooks[id];
				hookupEls[i].removeAttribute('data-view-id');
			}
		}
		//copy remaining hooks back
		$.extend($view.hookups, hooks);
	};

	/**
	 *  @add jQuery.fn
	 */
	funcs = [
	/**
	 *  @function prepend
	 *  @parent jQuery.View
	 *  abc
	 */
	"prepend",
	/**
	 *  @function append
	 *  @parent jQuery.View
	 *  abc
	 */
	"append",
	/**
	 *  @function after
	 *  @parent jQuery.View
	 *  abc
	 */
	"after",
	/**
	 *  @function before
	 *  @parent jQuery.View
	 *  abc
	 */
	"before",
	/**
	 *  @function text
	 *  @parent jQuery.View
	 *  abc
	 */
	"text",
	/**
	 *  @function html
	 *  @parent jQuery.View
	 *  abc
	 */
	"html",
	/**
	 *  @function replaceWith
	 *  @parent jQuery.View
	 *  abc
	 */
	"replaceWith", 
	"val"];

	//go through helper funcs and convert
	for ( var i = 0; i < funcs.length; i++ ) {
		convert(funcs[i]);
	}

})(jQuery);
