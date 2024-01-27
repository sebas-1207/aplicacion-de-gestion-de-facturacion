package com.campusland.views;

import java.time.LocalDateTime;
import java.util.List;

import com.campusland.respository.models.Cliente;
import com.campusland.respository.models.Factura;
import com.campusland.respository.models.ItemFactura;
import com.campusland.respository.models.Producto;

public class ViewFactura extends ViewMain {

    public static void startMenu() {

        int op = 0;

        do {

            op = mostrarMenu();
            switch (op) {
                case 1:
                    crearFactura();
                    break;
                case 2:
                    listarFactura();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (op >= 1 && op < 6);

    }

    public static int mostrarMenu() {
        System.out.println("----Menu--Factura----");
        System.out.println("1. Crear factura.");
        System.out.println("2. Listar factura.");
        System.out.println("3. Salir ");
        return leer.nextInt();
    }

    public static void listarFactura() {
        System.out.println("Lista de Facturas");
        for (Factura factura : serviceFactura.listar()) {
            factura.display();
            System.out.println();
        }
    }

    public static void crearFactura() {
        System.out.println("Seleccione un cliente: ");
        List<Cliente> clientes = serviceCliente.listar();
        int index = 1;
        for (Cliente cliente : clientes) {
            System.out.println(index + ". " + cliente.getNombre());
            index++;
        }

        int indiceCliente = leer.nextInt();
        Cliente clienteSeleccionado = clientes.get(indiceCliente - 1);

        System.out.println("Cliente seleccionado: " + clienteSeleccionado.getNombre());

        System.out.println("Seleccione productos para la factura (Ingrese 0 para finalizar): ");
        List<Producto> productos = serviceProducto.listar();
        Factura nuevaFactura = new Factura(LocalDateTime.now(), clienteSeleccionado);

        while (true) {
            System.out.println("Lista de Productos:");
            index = 1;
            for (Producto producto : productos) {
                System.out.println(index + ". " + producto.getNombre());
                index++;
            }

            int indiceProducto = leer.nextInt();

            if (indiceProducto == 0) {
                break;
            }

            Producto productoSeleccionado = productos.get(indiceProducto - 1);

            System.out.println("Ingrese la cantidad del producto seleccionado: ");
            int cantidad = leer.nextInt();

            ItemFactura itemFactura = new ItemFactura(cantidad, productoSeleccionado);
            nuevaFactura.agregarItem(itemFactura);

            System.out.println("Producto añadido a la factura: " + productoSeleccionado.getNombre());
        }

        serviceFactura.crear(nuevaFactura);
        System.out.println("Factura creada exitosamente.");
    }

}
