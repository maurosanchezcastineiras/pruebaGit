import React from "react";

const Cesta = ({ cesta }) => {
    return (
        <div className="container mt-4">
            <h1 className="mb-4">Cesta de Compra</h1>
            {cesta.length === 0 ? (
                <div className="alert alert-info">
                    No hay productos. No quieres comprar nada??
                </div>
            ) : (
                <div>
                    {cesta.map((producto, index) => (
                        <div key={index} className="card mb-3">
                            <div className="card-body">
                                <h5 className="card-title">{producto.articulo}</h5>
                                <p className="card-text">Precio: {producto.precio} €</p>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Cesta;