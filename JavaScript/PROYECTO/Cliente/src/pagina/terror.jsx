import React from "react";

const Terror = ({ datos, anadirCesta }) => {
    if (!datos || !datos.id) {
        return <p className="text-danger">Opción no válida</p>;
    }

    return (
        <div className="border border-primary p-3 mb-3 rounded">
            <p className="h5">{datos.articulo}</p>
            <p>Precio: {datos.precio} €</p>
            <button onClick={() => anadirCesta(datos)} className="btn btn-success">Añadir a la cesta</button>
        </div>
    );
};

export default Terror;