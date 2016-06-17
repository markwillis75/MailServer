<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Mail Client 0.1</title>
    </head>
    <body>
        <h2>Create an Email</h2>
        <form method="POST" action="mail" enctype="multipart/form-data">
            <input type="hidden" name="action" value="sendMail"/>
            TO: 
            <input type="text" name="to"><br/><br/>
            CC: 
            <input type="text" name="cc"><br/><br/>
            BCC: 
            <input type="text" name="bcc"><br/><br/>
            Subject: 
            <input type="text" name="subject"><br/><br/>
            Body<br/>
            <textarea name="body" rows="5" cols="30"></textarea><br/><br/>
            <b>Attachments</b><br/>
            <input type="file" name="attachment"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
