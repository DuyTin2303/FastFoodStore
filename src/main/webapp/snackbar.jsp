<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String successMessage = (String) session.getAttribute("success");
    String errorMessage = (String) session.getAttribute("error");
    if (successMessage != null) {
        session.removeAttribute("success");
    }
    if (errorMessage != null) {
        session.removeAttribute("error");
    }
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/31a6f4185b.js" crossorigin="anonymous"></script>
    <style>
        .snackbar {
            visibility: hidden;
            min-width: 250px;
            max-width: 400px;
            background-color: #333;
            color: #fff;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            gap: 10px;
            border-radius: 5px;
            padding: 16px;
            position: fixed;
            right: 20px;
            top: 30px;
            z-index: 1000;
            font-size: 17px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
        }
        .snackbar.show {
            visibility: visible;
            animation: fadein 0.5s, fadeout 0.5s 2.5s;
        }
        .snackbar i {
            font-size: 25px;
        }
        @keyframes fadein {
            from {
                opacity: 0;
                transform: translateX(10px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }
        @keyframes fadeout {
            from {
                opacity: 1;
                transform: translateX(0);
            }
            to {
                opacity: 0;
                transform: translateX(10px);
            }
        }
    </style>
</head>
<% if (successMessage != null) { %>
<div id="snackbar-success" class="snackbar" style="background-color: green;">
    <i class="fa-solid fa-circle-check"></i> <%= successMessage %>
</div>
<script>
    window.onload = function () {
        var x = document.getElementById("snackbar-success");
        x.classList.add("show");
        setTimeout(function () {
            x.classList.remove("show");
        }, 3000);
    };
</script>
<% } %>

<% if (errorMessage != null) { %>
<div id="snackbar-error" class="snackbar" style="background-color: red;">
    <i class="fa-solid fa-circle-xmark"></i> <%= errorMessage %>
</div>
<script>
    window.onload = function () {
        var x = document.getElementById("snackbar-error");
        x.classList.add("show");
        setTimeout(function () {
            x.classList.remove("show");
        }, 3000);
    };
</script>
<% } %>

