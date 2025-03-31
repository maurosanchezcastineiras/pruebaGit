import React from "react";
import { useNavigate } from "react-router-dom";

const WebInicioSinLogin = () => {
  const navigate = useNavigate();

  const Login = () => {
    navigate("/login"); 
  };

  return (
    <div className="principal">
        <button type="button" className="btn btn-warning mb-3" onClick={Login} > Inicio de Sesión </button>
    </div>
  );
};

export default WebInicioSinLogin;
