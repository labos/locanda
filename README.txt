LOCANDA - version 0.4

Thanks for trying Locanda. In this file you'll find some useful information to configure it according to the needs of your structure, and to start using it.

ACCESS TO LOCANDA
From the home page (http://localhost:8080/locanda), clicking on the "Login/Signup" button in the top-right part of the page will redirect you to the Login page
If you don't have an account yet, by clicking on the "Signup" button you will reach a form for entering your personal data. Once submitted this form, you will be given a password, to be used along with the email address entered in the form.
You are now able to successfully login into the system. The main page of Locanda will thus appear, that shows a resume of the current and past bookings (if present), apart from all the cascade menus for reaching all Locanda sections.

PRE-LOADED DATA
A structure with some example data is already present. It's freely editable for inserting your own data. All the other entities (rooms, room types, seasons, extras etc.) must be inserted by the user.
As far as the price lists are concerned (both rooms and extras), at least a season and a room type must exist. The relative price list will be created with all prices set to zero (0.0). The user is thus requested to manually edit the prices for each week day (rooms) and for each extra.
Some entities depend on some others. As an example, you cannot create rooms without any room type, or bookings without rooms and price list elements (seasons, room types, conventions). Regarding conventions, creating a new one is not necessary, because the default options, that allows the creation of price lists, is "No convention".

To sum up, in order to have all the necessary data to start using Locanda, the following steps are recommended:
-create one or more seasons with the relative periods
-create one or more room types
-create one or more rooms
-create one or more extras with the relative price types (i.e. per Night/per Person)
-configure the room price lists
-configure the extra price lists

Now you can insert a booking directly in the planner or by clicking the "New Booking" button.
Please give a try also to the HTML component that enables another web site to collect bookings and store them in the system; you can find it in the section "Settings->Online Widget", clicking on the link "Try the Online Widget"


IMAGES
Images can be used to better describe the resources of your structure.
They can be associated with the structure, a room type, a single room, a facility.

FACILITIES
With Locanda you can insert the facilities of your structure and rooms, with an associated image. Some are already present, but you can easily others with your own images. A gallery with the most common and useful facility images is available, so you can use them for your custom facilities. They are in the folder "/facilities".

REPORTS
Starting from v0.2 is the creation of the booking invoice. In the booking page, there is a button called "Download Invoice". By clicking on it, a PDF file with the invoice with booking's saved data will be generated.

SEARCH
Starting from v0.3 each resource of the system can be searched by using a simple text field or an advanced search form, in the top left part of the page. This search is powered by SOLR, which can be easily configured with the help of the installation instructions file

HOUSED
Starting from v0.4, you can set for each booking, at the check-in, each person housed in that room.

Have fun with Locanda!

## How to run the application

First of all you need to use JDK 1.7. I'm using Tomcat 7 as application server, you can download the *CORE* version [here](http://tomcat.apache.org/download-70.cgi).

Now you need to import all libraries specified in pom.xml and run two maven commands:
* clean
* install

The install command will create a new folder called Locanda_target in which you can find Locanda.war. Execute this war in your server and, if you had no error, you should see the web application running in your browser (http://localhost:8080/).

