import { useEffect, useState } from "react";
import "./style.css";
import { getResumenMensualUsuario } from "../../utilities/utilitie";

const CircularGraphic = ({goToLogin}) => {
  var [dataGraphic, setDatadataGraphic] = useState(false);
  useEffect(() => {
    getResumenData();
  }, []);

  async function getResumenData() {
    const response = await getResumenMensualUsuario(
      document.cookie.split("=")[1]
    );
    if (response === 403) {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    } else {
      var n = [["Type", "Count"]];
      var data = response.body;
      Object.keys(response.body).forEach((key, index) => {
        n.push([key, data[key]]);
      });
      if(n.length <= 1){
        // Vacio no existe datos
      }else{
        setDatadataGraphic(n);
      }
    }
  }
  if(dataGraphic){
    google.charts.load("current", { packages: ["corechart"] });
    google.charts.setOnLoadCallback(drawChart);
  }

  function drawChart() {
    var data = google.visualization.arrayToDataTable(dataGraphic);

    var options = {
      title: "Operaciones Mensual",
      titleTextStyle: {
        fontSize: 22, // Tamaño del título
        bold: true, // Título en negrita
        color: "white", // Color del título
      },
      legend: { position: "bottom", textStyle: { fontSize: 9.2, bold: true ,color:"white"} },
      backgroundColor: "transparent", // Fondo transparente
    };

    var chart = new google.visualization.PieChart(
      document.getElementById("piechart")
    );

    chart.draw(data, options);
  }

  return (!dataGraphic)?<></>:<div id="piechart"></div>
};

export const TotalSaldo = () => {
  return <h1></h1>;
};

export default CircularGraphic;
