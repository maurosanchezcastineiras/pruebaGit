import React from "react";

const TiendaCategoria = ({ datos }) => {
    if (!datos || !datos.id) {
        return <p>Opción no válida</p>;
    }

    return (
        <div style={{ border: "3px solid green", padding: "10px", margin: "20px 0" }}>
            <p>Categoria{datos.id}</p>
            <p> {datos.clase}</p>
        </div>
    );
};

export default TiendaCategoria;