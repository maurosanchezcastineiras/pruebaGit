import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const FormularioClienteEditar = () => {
    const { id } = useParams();
    const navega = useNavigate();

    const [formulario, setFormulario] = useState({
        "usuario": "",
        "contrasena": "",
        "jardin_asociado": ""
    });

    const [jardines, setJardines] = useState([]);

    useEffect(() => {
        const buscaJardines = async () => {
            try {
                const resultado = await axios.get("http://localhost:4000/jardines");
                setJardines(resultado.data);
            } catch (error) {
                console.log(error);
            }
        };

        const buscaCliente= async () => {
            try {
                const resultado = await axios.get(`http://localhost:4000/clientes/${id}`);
                setFormulario({
                    "usuario": resultado.data[0].usuario,
                    "contrasena": resultado.data[0].contrasena,
                    "jardin_asociado": resultado.data[0].jardin_asociado
                });
            } catch (error) {
                console.log(error);
            }
        };

        buscaJardines();
        buscaCliente();
    }, [id]); 

    const editarCliente = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:4000/clientes/${id}`, formulario);
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
            <h2>Modificar un Cliente</h2>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="usuario" name="usuario" value={formulario.usuario}></input>
            <br/>
            <input className="col-form-label col-form-label-sm mt-4" type="text" onChange={gestionFormulario} id="contrasena" name="contrasena" value={formulario.contrasena}></input>
            <br/>
            <select  id="btnGroupDrop1" type="button" className="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                name="jardin_asociado" onChange={gestionFormulario} value={formulario.jardin_asociado}>
                {
                    jardines.map((jardin) =>{
                        return(
                            <option key={jardin.id} value={jardin.id}>{jardin.jardin}</option>
                        )
                    })
                }
            </select>
            <br/>
            <br/>
            <button type="button" className="btn btn-success" onClick={editarCliente} name="editarCliente">Actualizar cambios</button>

        </>
    )
}

export default FormularioClienteEditar
