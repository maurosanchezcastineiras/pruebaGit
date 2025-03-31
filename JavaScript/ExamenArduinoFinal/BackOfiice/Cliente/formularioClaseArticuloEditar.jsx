import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

const formularioClaseArticuloEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [articulos, setArticulos] = useState({
        "articulo": "",
        "clase_articulo": ""
    });

    const [claseArticulos, setClaseArticulos] = useState([]);

    useEffect(() => {
        const buscaArticulos = async () => {
            if (id) { 
                try {
                    const resultado = await axios.get(`http://localhost:4000/articulos/${id}`);
                    setFormulario(resultado.data)
                } catch (error) {
                    console.log(error);
                }
            }
        };        
    
        const buscaClaseArticulos = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/claseArticulos");
                setClaseArticulos(resultado.data);
            } catch (error) {
                console.log(error);
            }
        };
    
        buscaArticulos();
        buscaClaseArticulos();
    }, [id]);

    const editarArticulo = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:4000/articulos/${id}`, articulos);
        } catch (error) {
            console.log(error);
        }
        navega("/");
    };

    const gestionArticulos = (e) => {
        setArticulos((anterior) => ({ ...anterior, [e.target.name]: e.target.value }));
    };

    return (
        <>
            <h2>Actualizar un Artículo</h2>
            <br/>
            <input
                type="text"
                onChange={gestionArticulos}
                id="nombre"
                name="nombre"
                value={articulos.articulo}
            ></input>
            <br/>
            <br/>
            <select
                type="button" 
                className="btn btn-primary"
                id="clase_articulo_id"
                name="clase_articulo_id"
                onChange={gestionArticulos}
                value={articulos.clase_articulo}
            >
                {claseArticulos.map((claseArticulo) => {
                    return (
                        <option key={claseArticulo.id} value={claseArticulo.id}>
                            {claseArticulo.clase}
                        </option>
                    );
                })}
            </select>
            <br/>
            <br/>
            <button type="button" className="btn btn-dark" onClick={editarArticulo} name="editarArticulo">
                Actualizar cambios
            </button>
        </>
    );
};

export default formularioClaseArticuloEditar;
