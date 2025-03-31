import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const FormularioRangoHumedadSueloEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [formulario, setFormulario] = useState({
        "idealMinimo": "",
        "idealMaximo": ""
    });

    useEffect(() => {
        const buscaRangos = async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/rangosHumedadSuelo/${id}`);
                setFormulario({
                    "idealMinimo": resultado.data[0].idealMinimo,
                    "idealMaximo": resultado.data[0].idealMaximo
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
            await axios.put(`http://localhost:4000/rangosHumedadSuelo/${id}`, formulario);
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
            <h2>Modificar los Rangos</h2>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="idealMinimo" name="idealMinimo" value={formulario.idealMinimo}></input>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="idealMaximo" name="idealMaximo" value={formulario.idealMaximo}></input>
            <br/>
            <button type="button" className="btn btn-success" onClick={editarRangos} name="editarRangos">Actualizar cambios</button>

        </>
    )
}

export default FormularioRangoHumedadSueloEditar
