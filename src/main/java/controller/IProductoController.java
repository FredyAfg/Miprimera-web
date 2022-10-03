package controller;

public interface IProductoController {
    
    /* Listar productos - YA */
    public String listar(boolean ordenar, String orden);

    /* Comprar productos */
    public String alquilar(int id_producto, String username);

    /* Modificar productos - YA */
    public String modificar(int id_producto);

    /* Devolver productos - YA */
    public String devolver(int id_producto, String username);

    /* Sumar cantidad - YA */
    public String sumarCantidad(int id_producto);

}
