package taxis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Factura {

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    private String nombre_archivo, concepto;
    private Date fecha_creacion, fecha_uso_taxi;
    private double importe;
    private int idFactura = 0;
    private static int id = 0;

    public Factura(String nombre_archivo, String concepto, Date fecha_creacion, Date fecha_uso_taxi, double importe) {
        super();
        this.nombre_archivo = nombre_archivo;
        this.concepto = concepto;
        this.fecha_creacion = fecha_creacion;
        this.fecha_uso_taxi = fecha_uso_taxi;
        this.importe = importe;
        this.idFactura = idFactura;
        Factura.id++;
        idFactura = id;
    }

    @Override
    public String toString() {
        return "Factura [id=" + idFactura + ", nombre_archivo=" + nombre_archivo + ", concepto=" + concepto
                + ", fecha_creacion=" + formatoFecha.format(fecha_creacion) + ", fecha_uso_taxi="
                + formatoFecha.format(fecha_uso_taxi) + ", importe=" + importe + "]";
    }

    public static int getId() {
        return id;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_uso_taxi() {
        return fecha_uso_taxi;
    }

    public void setFecha_uso_taxi(Date fecha_uso_taxi) {
        this.fecha_uso_taxi = fecha_uso_taxi;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public static void borrarFactura(List<Factura> facturas, Factura f) {

        if (facturas.contains(f)) {

            facturas.remove(f);
            id--;
        }
    }

    public static String conseguirConcepto(File archivo) {

        String nombreArchivo = archivo.getName();
        int num_guiones = 0, segundaPosicion = 0, terceraPosicion = 0;
        String concepto = "";

        for (int i = 0; i < nombreArchivo.length(); i++) {

            String caracter = String.valueOf(nombreArchivo.charAt(i));

            if (caracter.equalsIgnoreCase("_")) {
                num_guiones++;

                if (num_guiones == 2)
                    segundaPosicion = i;

                if (num_guiones == 3)
                    terceraPosicion = i;
            }

        }

        if (num_guiones == 3)
            concepto = nombreArchivo.substring(0, segundaPosicion);

        if (num_guiones == 4)
            concepto = nombreArchivo.substring(0, terceraPosicion);

        return concepto;
    }

    public static Date conseguirFechaRecibo(File archivo) {

        String nombreArchivo = archivo.getName();
        int num_guiones = 0, segundaPosicion = 0, terceraPosicion = 0, cuartaPosicion = 0;
        String fechaRecibo = null;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < nombreArchivo.length(); i++) {

            String caracter = String.valueOf(nombreArchivo.charAt(i));

            if (caracter.equalsIgnoreCase("_")) {
                num_guiones++;

                if (num_guiones == 2) segundaPosicion = i;
                if (num_guiones == 3) terceraPosicion = i;
                if (num_guiones == 4) cuartaPosicion = i;
            }

        }

        if (num_guiones == 3)
            fechaRecibo = nombreArchivo.substring(segundaPosicion + 1, terceraPosicion).replaceAll("\\.", "/");

        if (num_guiones == 4)
            fechaRecibo = nombreArchivo.substring(terceraPosicion + 1, cuartaPosicion).replaceAll("\\.", "/");

        try {

            return formateador.parse(fechaRecibo);

        } catch (Exception e) {
            return null;
        }
    }

    public static double conseguirImporte(File archivo) {

        String nombreArchivo = archivo.getName();
        int posicionUltimoGuion = nombreArchivo.lastIndexOf("_");
        int posicionUltimoPunto = nombreArchivo.lastIndexOf(".");

        double importe = Double
                .valueOf(nombreArchivo.substring(posicionUltimoGuion + 1, posicionUltimoPunto).replace(",", "."));

        return importe;
    }
}
