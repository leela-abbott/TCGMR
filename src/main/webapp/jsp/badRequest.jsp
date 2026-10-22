<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>400 Bad Request</title>
    <style>
        body { 
            font-family: Arial, Helvetica, sans-serif; 
            text-align: center; 
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #ffffff; 
            color: #555555;
        }
        main {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        h1 { 
            color: #e53e3e; 
            font-size: 96px; 
            font-weight: bold;
            margin: 0 0 16px 0;
            letter-spacing: -0.02em;
            line-height: 1;
        }
        p { 
            font-size: 32px; 
            font-weight: bold;
            color: #555555;
            margin: 0 0 40px 0;
            line-height: 1.2;
        }
        button {
            /* Yellow gradient style matching the image */
            background: linear-gradient(to bottom, #ffe066 0%, #f5b000 100%);
            border: 1px solid #d49600;
            border-radius: 5px;
            
            color: #0a1172;
            font-family: "Arial Black", Gadget, sans-serif;
            font-size: 12px;
            font-weight: 900;
            
            padding: 8px 20px;
            cursor: pointer;
            box-shadow: 2px 3px 5px rgba(0, 0, 0, 0.2);
            transition: opacity 0.1s ease;
        }
        button:hover {
            opacity: 0.9;
        }
        button:active {
            box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
            transform: translateY(1px);
        }
    </style>
</head>
<body>

    <main>
        <h1>400</h1>
        <p>Bad Request</p>
        <button onclick="window.history.back();">GO BACK</button>
    </main>

</body>
</html>
