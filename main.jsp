<html>
  <head>
    <title> Captcha </title>
    <style>
      body {
        text-align: center;
        margin-left: auto;
        margin-right: auto;
      }
      .wow {
        font-size: 3vw; 
        font-family: sans-serif; 
        background: linear-gradient(to right, rgba(255, 100, 100, 0) 0%, rgba(225, 255, 255, 0.5) 20%, rgba(255, 255, 255, 0) 61%), linear-gradient(rgb(97, 100, 217) 52%, rgb(224, 246, 255) 60%, rgb(100, 255, 100) 61%); 
        background-clip: text; 
        -webkit-background-clip: text; 
        -webkit-text-fill-color: transparent; 
        animation: wave 1000ms ease alternate infinite;
        transition: all 0.4s ease;
      }
      @keyframes wave {
        0% {
          background-position: 0 0;
        }
        100% {
          background-position: 50vw 50px;
        }
      }
    </style>
  </head>
  <body>
	  <form id="form" autocomplete="off" action="salvar" method="post">
	    <h1 class="wow">Welcome! Please copy the text below to proceed:</h1>
	    <% 

	    String randomString = (String) request.getAttribute("random_string");
	    out.print("<h1 id=\"randString\">" + randomString + "</h1>");
	  
	    %>
	    <input id="sequence" type="text" name="string" value="" required>
      <input id="fButton" type="submit" value="submit" disabled>
    </form>
    <h1>Resultado: <p id="result"></p></h1>
  </body>
  <script>
    const formButton = document.getElementById("fButton");
    const strInput = document.getElementById("sequence");
    const randomStr = document.getElementById("randString");
    formButton.disabled = true;
    strInput.value = "";
    function validateString(){
       if (strInput.value == randomStr.innerHTML)
         formButton.disabled = false;
       else
         formButton.disabled = true;
    } 
    strInput.onpaste = e => {
      e.preventDefault();
      return false;
    };
    strInput.onkeyup = e => {
      strInput.value = strInput.value.toUpperCase();
      validateString();
      return true;
    }
  </script>
  <script>
    const formulario = document.getElementById("form");
    formulario.onsubmit = async (e) => {
      e.preventDefault();
      let response = await fetch('/FullStack_Java_JS_App/Verify', {
        method: 'POST',
        body: new FormData(formulario)
      });
      resultado = await response.json();
      document.getElementById("result").innerHTML = resultado.msg;
    };
  </script>
</html>
