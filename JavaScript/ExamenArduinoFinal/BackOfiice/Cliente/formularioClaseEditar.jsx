import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const FormularioClaseEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [clases, setClases] = useState({
        "clase": "",
    });

    useEffect(() => {
        const buscaClase = async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/claseArticulos/${id}`);
                setFormulario({
                    "clase": resultado.data[0].clase
                });
            } catch (error) {
                console.log(error);
            }
        };
        buscaClase();
    }, [id]); 

    const editarClase = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:4000/claseArticulos/${id}`, clases);
            navega("/");
        } catch (error) {
            console.log(error);
        }  
    };

    const gestionClases = (e) => {
        setClases((anterior) => ({ ...anterior, [e.target.name]: e.target.value }));
    };

    return(
        <>
            <h2>Actualizar una Clase</h2>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionClases} id="clase" name="clase" value={clases.clase}></input>
            <br/>
            <br/>
            <button type="button" className="btn btn-success" onClick={editarClase} name="editarClase">Actualizar cambios</button>
            
            <Articulos articuloId={id} />

        </>
    )
}

export default FormularioClaseEditar
