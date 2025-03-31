import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import Articulo from "./articulo"

const Articulos = ({ claseArticuloId }) => {
    const navega = useNavigate();
    const [articulos, setArticulos] = useState([]);
    
    useEffect(() => {
        
        const buscaArticulos = async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/articulos?ClaseArticuloId=${claseArticuloId}`);
                setArticulos(resultado.data);
            } catch (error) {
                console.error("Error al cargar los artículos:", error);
            }
        };

        if (claseArticuloId) {
            buscaArticulos();
        }
    }, [claseArticuloId]); 

    const anadeArticulo = () => {
        navega("/formularioClaseArticulo");
    };    
    
    return (
        <>
            <br/>
            <br/>
            <br/>
            <h3>
                <small className="text-body-secondary">ARTÍCULOS DEL{claseArticuloId}</small>
            </h3>
            <br/>
            {articulos.length === 0 ? (
                <p className="text-body-secondary">No hay artículos relacionados con {claseArticuloId}</p>
            ) : (
                articulos.map((articulo) => (
                    articulo && articulo.id ? (
                        <Articulo key={articulo.id} datos={articulo} />
                    ) : null
                ))
            )}
            <br/>
            <button type="button" className="btn btn-primary" onClick={anadeArticulo} name="anadeArticulo">Añadir un Artículo</button>
        </>
    );
};

export default Articulos;
