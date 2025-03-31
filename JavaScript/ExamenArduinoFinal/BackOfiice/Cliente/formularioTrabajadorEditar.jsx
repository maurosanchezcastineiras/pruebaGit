import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const FormularioTrabajadorEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [formulario, setFormulario] = useState({
        "nombre": "",
        "apellidos": "",
        "rol_asociado": ""
    });

    const [roles, setRoles] = useState([]);

    useEffect(() => {
        const buscaRoles = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/roles");
                setRoles(resultado.data);
            } catch (error) {
                console.log(error);
            }
        };

        const buscaTrabajador = async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/trabajadores/${id}`);
                setFormulario({
                    "nombre": resultado.data[0].nombre,
                    "apellidos": resultado.data[0].apellidos,
                    "rol_asociado": resultado.data[0].rol_asociado
                });
            } catch (error) {
                console.log(error);
            }
        };

        buscaRoles();
        buscaTrabajador();
    }, [id]); 

    const editarTrabajador = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:4000/trabajadores/${id}`, formulario);
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
            <h2>Modificar un Trabajador</h2>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="nombre" name="nombre" value={formulario.nombre}></input>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="apellidos" name="apellidos" value={formulario.apellidos}></input>
            <br/>
            <select  id="btnGroupDrop1" type="button" className="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                name="rol_asociado" onChange={gestionFormulario} value={formulario.rol_asociado}>
                {
                    roles.map((rol) =>{
                        return(
                            <option key={rol.id} value={rol.id}>{rol.rol}</option>
                        )
                    })
                }
            </select>
            <br/>
            <br/>
            <button type="button" className="btn btn-success" onClick={editarTrabajador} name="editarTrabajador">Actualizar cambios</button>

        </>
    )
}

export default FormularioTrabajadorEditar
