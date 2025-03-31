import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const FormularioNivelAguaEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [formulario, setFormulario] = useState({
        "nivelMinimo": ""
    });

    useEffect(() => {
        const buscaRangos = async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/nivelAgua/${id}`);
                setFormulario({
                    "nivelMinimo": resultado.data[0].nivelMinimo
                });
            } catch (error) {
                console.log(error);
            }
        };
        buscaRangos();
    }, [id]); 

    const editarRangos = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:4000/nivelAgua/${id}`, formulario);
            navega("/");
        } catch (error) {
            console.log(error);
        }  
    };

    const gestionFormulario = (e) => {
        setFormulario((anterior) => ({ ...anterior, [e.target.name]: e.target.value }));
    };

    return(
        <>
            <h2>Modificar el Nivel</h2>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="nivelMinimo" name="nivelMinimo" value={formulario.nivelMinimo}></input>
            <br/>
            <button type="button" className="btn btn-success" onClick={editarRangos} name="editarRangos">Actualizar cambios</button>

        </>
    )
}

export default FormularioNivelAguaEditar
