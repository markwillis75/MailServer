Instructions for use
====================
1. Drop the /dist/MailServer.war file into Tomcat
2. Browse to the following exploded directory <Tomcat-install>/webapps/MailServer/WEB-INF/classes/com/mkwillis/mailserver/server/smtp
3. Edit the smtpserver.properties file, entering relevant values for the following fields:
     - mail.smtp.host
	 - mail.smtp.user
	 - mail.smtp.password
4. Navigate to http://host:port/MailServer
5. On the resultant page, enter the relevant email details and hit the Submit button.
     - The program is built to tolerate both valid and invalid values in the smtpserver.properties file and will generate a success/failure page for the user
	 
It is the use of the underlying properties file which allows us to switch to different third party mail delivery systems as required.

Assumptions
===========
1.
Zero down-time isn't a requirement, so the current implementation loads the smtpservers.properties file once.
If zero down-time was a requirement, we could implement a 2nd login-protected admin servlet allowing the user to change the in-memory Properties object in SMTPMailHandler without a need to stop/restart the web-app.

With the current implementation, changing the values in the smtpserver.properties files necessitates a stop and restart of the app via Tomcat

2. 
I have avoided implementing login functionality to capture the current user from which to extract the sender address and have coded a default sender address.

3. 
A valid email need consist only of a recipient (TO/CC/BCC).  If they so wished, a user could send a mail with subject, body or attachments

4.
I have kept the recpient fields simple.
 - They assume the user will enter a single well-formed address into at least one of the TO/CC/BCC fields
 - I have not implemented support to ensure that the value is well-formed, but such a requirement could be fulfilled easily with a regex check in the MailValidator.validate(IEmail) method
 - Similarly, I have not implemented support for more than a single address on either of the TO/CC/BCC fields.  This could easily be achieved with a regex split on the Strings as they are received by the EMailFactory.setRecipients(...) method, storing the individual addresses in the EmailImpl class in a **Collection** keyed against IEmail.Visibility rather than as a **String** keyed against IEMail.Visibility.  Then at the transport layer (SMTPMailHandler), we would map each address in the Collection to a JavaMail InternetAddress.
 
5. The bean classes (EmailImpl, AttachmentImpl, SendResult etc) should - to be considered canonical objects - should override the inherited equals(), hashCode() and toString() methods, but as they aren't being used as keys in any collections/maps and I'm performing no comparison on them, I've avoided including them in the interests of brevity (with the exception of EmailImpl, which I included only as an illustration)

6. The meat of what this task is about is the Model.  A pretty front-end wasn't a requirement and would have been nothing more than window-dressing.  I have intentionally avoided wasting time on that by avoiding CSS styling and Javascript validation etc.

Notes on design
===============
We have a typical MVC architecture, where the servlet acts as the Controller, the JSP pages as the View and everything behind the Controller acting as the Model.

The MailManager class is a Facade for the controller - hiding details of how the underlying mail mechanism works and giving the Controller an intentionally limited view.

Similarly, the EmailFactory gives the Controller a single point of entry into email contstruction and exposes only an IEmail interface to the Controller.  This avoids the need for the Controller to know about concrete implementations of the IEmail and IAttachment interfaces.

The MailManager is a singleton and uses java Reflection to intstantiate the object which will actually transmit the mail (in our example, SMTPMailHandler).  This allows us to implement a form of Dependency Injection, injecting the SMTPMailHandler into the MailService at runtime, achieving Inversion of Control, where the MailService is simply a proxy to the SMTPHandler, to which it delegates the responsibility of sending the mail.

Where a class had no need to define or maintain state, I've favoured the use of static utility classes - see EmailFactory and MailValidator

Coding to interfaces instead of implementation is used as much as possible.  The creation of IEmail and IAttachment interfaces, along with concrete subclasses, and having the Model deal with those objects right up until point of integration with the JavaMail API is intentional.  It keeps the model agnostic to the method of transmission until the last minute and divorces the View and Controller completely from knowing about the transmission layer.  It also means the Model is completely agnostic to the View and Controller implementation - the entire model code could be reused as-is by a desktop application instead of web-app.

