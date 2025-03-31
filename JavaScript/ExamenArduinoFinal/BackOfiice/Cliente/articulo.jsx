import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Articulo = ({ datos }) => {
    const navega = useNavigate();

    if (!datos || !datos.id) {
        return <p>Artículo no válido</p>;
    }

    const IrAFormularioClaseArticuloEditar = (dato) =>{
        navega("/formularioCatalogoArticuloEditar/" + dato)
    }

    const borrarArticulo = async (dato) =>{
        await axios.delete("http://localhost:4000/articulos/" + dato)
        location.reload()
    }

    return (
        <div style={{ border: "3px solid blue", padding: "10px", margin: "10px 0" }}>
            <p><strong>Articulo </strong> {datos.id}</p>
            <p>{datos.articulo}</p>
            <br/>
            <button type="button" className="btn btn-info" onClick={() => IrAFormularioClaseArticuloEditar(datos.id)}>Editar</button>
            <br/>
            <br/>
            <button type="button" className="btn btn-secondary" onClick={() => borrarArticulo(datos.id)}>Borrar</button>
        </div>
    );
};

export default Articulo;
