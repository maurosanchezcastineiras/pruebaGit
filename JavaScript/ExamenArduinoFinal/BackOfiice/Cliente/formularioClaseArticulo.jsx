import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const FormularioClaseArticulo = () => {
    const navega = useNavigate();
    const [articulos, setArticulos] = useState({
        "articulo": "",
        "clase_articulo": ""
    });

    const [claseArticulos, setClaseArticulos] = useState([]);

    useEffect(() => {
        const buscaClaseArticulos = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/claseArticulos");
                setClaseArticulos(resultado.data);
            } catch (error) {
                console.error("Error al cargar las clases:", error);
            }
        };
        buscaClaseArticulos();
    }, []);

    const enviarArticulo = async (e) => {
        e.preventDefault();
        try {
            const respuesta = await axios.post("http://localhost:4000/articulos", {
                articulo: articulos.articulo,
                clase_articulo: articulos.clase_articulo
            });
            console.log("Artículo añadido:", respuesta.data);
            navega("/"); 
        } catch (error) {
            console.error("Error al enviar el artículo:", error);
            alert("Hubo un error al añadir el artículo.");
        }
    };      

    const gestionArticulos = (e) => {
        setArticulos((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    return (
        <>
            <h2>Agregar un Artículo</h2>
            <br/>
            <form onSubmit={enviarArticulo}>
                <input
                    type="text"
                    onChange={gestionArticulos}
                    id="nombre"
                    name="nombre"
                    value={articulos.articulo}
                    placeholder="Nombre del Artículo"
                />
                <br/>
                <br/>
                <select
                    type="button" 
                    className="btn btn-info dropdown-toggle" 
                    data-bs-toggle="dropdown" 
                    aria-haspopup="true"
                    id="clase_articulo_id"
                    name="clase_articulo_id"
                    onChange={gestionArticulos}
                    value={articulos.clase_articulo}
                >
                    <option value="">Elige una Clase</option>
                    {claseArticulos.map((claseArticulo) => (
                        <option key={claseArticulo.id} value={claseArticulo.id}>
                            {claseArticulo.clase}
                        </option>
                    ))}
                </select>
                <br/>
                <br/>
                <button className="btn btn-warning" type="submit">Enviar Artículo</button>
            </form>
        </>
    );
};

export default FormularioClaseArticulo;
