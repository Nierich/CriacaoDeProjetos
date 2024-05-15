/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

    function pegarId (){
        const formulario = document.querySelector("#formulario");
        const idSelect = document.querySelector("#idSelect");
        let id = idSelect.value;
        formulario.action = "acao?a=gerarRelatorio&id=" + id;
        console.log("qualquer coisa");
    }
   
