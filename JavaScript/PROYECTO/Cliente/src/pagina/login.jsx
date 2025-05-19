import React, { useState } from "react";
import { initializeApp } from "firebase/app";
import { getFirestore, collection, query, where, getDocs } from "firebase/firestore";
import { useNavigate } from "react-router-dom";

const firebaseConfig = {
  apiKey: "AIzaSyD2lNhTeo3jXnPoo_OzgrtDrOtJq9B_PK4",
  authDomain: "ejemplo-219d9.firebaseapp.com",
  projectId: "ejemplo-219d9",
  storageBucket: "ejemplo-219d9.appspot.com",
  messagingSenderId: "729196906554",
  appId: "1:729196906554:web:b64b7764a3609fc9b54d03",
};
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

const Login = () => {
  const [usuario, setUsuario] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const formulario = async (e) => {
    e.preventDefault();
    try {
      const q = query(collection(db, "clientes"),where("usuario", "==", usuario),where("contrasena", "==", contrasena));
      const querySnapshot = await getDocs(q);
      if (!querySnapshot.empty) {
        const cliente = querySnapshot.docs[0].data();
        const jardinAsociadoId = cliente.jardin_asociado; 
        // guarda el estado de autenticación y el ID del jardín asociado
        localStorage.setItem("estado", true);
        localStorage.setItem("jardin_asociado", jardinAsociadoId);
        navigate("/webInicio");
      } else {
        setError("Credenciales no válidas");
      }
    } catch (error) {
      console.error("Error:", error);
      setError("Error con FFirebase");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-50 bg-light">
      <div className="card p-4 shadow" style={{ width: "400px" }}>
        <h2 className="text-center mb-4">Iniciar Sesión</h2>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={formulario}>
          <div className="mb-3">
            <label htmlFor="usuario" className="form-label">Usuario:</label>
            <input type="text" id="usuario" className="form-control"value={usuario}  onChange={(e) => setUsuario(e.target.value)} required/>
          </div>
  
          <div className="mb-4">
            <label htmlFor="contrasena" className="form-label">Contraseña:</label>
            <input type="password" id="contrasena"className="form-control" value={contrasena} onChange={(e) => setContrasena(e.target.value)} required/>
          </div>
          <button type="submit" className="btn btn-primary w-100">Entrar</button>
        </form>
      </div>
    </div>
  );
}

export default Login;