package taxis;

import java.io.File;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Uso_taxis {

    public static void main(String[] args) {

        File rutaFacturas = new File("C:" + File.separator + "Users" + File.separator + "User" + File.separator
                + "Documents" + File.separator + "Accidente" + File.separator + "Taxis");

        ArrayList<String> listaArchivosEnRuta = new ArrayList<String>(Arrays.asList(rutaFacturas.list()));

        List<Factura> facturas = new ArrayList<Factura>();

        for (String a : listaArchivosEnRuta) {

            File f = new File(a);

            facturas.add(new Factura(f.getName(), Factura.conseguirConcepto(f), new Date(f.lastModified()), Factura.conseguirFechaRecibo(f), Factura.conseguirImporte(f)));
        }


        // PROGRAMACIÓN IMPERATIVA --------------------------------

//		// foreach tradicional
//		for (Factura f : facturas) {
//			System.out.println(f);
//		}

//		Factura f1 = new Factura("taxi.pdf", "taxi", Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"), 10.20);
//		Factura f2 = new Factura("taxi.pdf", "taxi", Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"), 10.20);
//		Factura f3 = new Factura("taxi.pdf", "taxi", Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"), 10.20);
//		facturas.add(f1);
//		facturas.add(f2);
//		facturas.add(f3);
//
//		Iterator<Factura> itFacturas = facturas.iterator();
//
//		while(itFacturas.hasNext()) {
//
//			if (itFacturas.next().getIdFactura() == 3) {
//
//				itFacturas.remove();
//				System.out.println("BORRADO EL ID 3");
//			}
//		}


        // PROGRAMACIÓN FUNCIONAL --------------------------------

        // FOREACH
//		facturas.forEach(p -> System.out.println(p));

        // FOREACH
//		facturas.forEach(System.out::println);

        // FOREACH
//		printList(facturas);

        // FILTER (parámetro: predicado true/false )
        List<Factura> f1 = facturas.stream()
                .filter(p -> p.getImporte() < 10)
                .collect(Collectors.toList());

//		System.out.println(f1.stream().count());

        // MAP (parámetro: función)
        List<Double> f2 = facturas.stream()
                //					.map(f -> f.getFecha_uso_taxi().getMonth())
                .map(f -> f.getImporte()*100)
                .collect(Collectors.toList());

        // SORTED (parámetro: comparador)
        Comparator<Factura> byDateAsc = (Factura fa, Factura fb) -> fa.getFecha_uso_taxi().compareTo(fb.getFecha_uso_taxi());
        Comparator<Factura> byAmountDesc = (Factura fa, Factura fb) -> Double.compare(fa.getImporte(), fb.getImporte());

        List<Factura> f3 = facturas.stream()
                .sorted(byAmountDesc)
                .collect(Collectors.toList());

        // ANYMATCH (no evalúa todo el stream, acaba cuando encuentra la coincidencia)
        boolean f_any = facturas.stream()
                .anyMatch(f -> f.getConcepto().equalsIgnoreCase("factura taxi_rehab"));

        System.out.println(f_any);

        // ALLMATCH (evalúa todo el stream y devuelve un true si todos cumplen la condición)
        boolean f_all = facturas.stream()
                .allMatch(f -> f.getConcepto().contains("culo"));

//		facturas.forEach(f -> System.out.println(f.getConcepto()));
        System.out.println(f_all);

        // NONEMATCH (evalúa todo el stream y devuelve un true si ninguno cumple la condición)
        Predicate<Factura> predicate = factura -> factura.getIdFactura() > 100;
        boolean f_none = facturas.stream()
                .noneMatch(predicate);

        System.out.println(f_none);

        // LIMIT devuelve los primeros x elementos
        // SKIP devuelve los restantes x elementos
        // para paginación, aquí mostramos de 5 en 5 a partir del skip
        int numeroPagina = 0, numElementosPorPagina = 5;
        List<Factura> f4 = facturas.stream()
                .skip(numeroPagina*numElementosPorPagina)
                .limit(numElementosPorPagina)
                .collect(Collectors.toList());

        // COLLECTORS
        // GROUP BY
        Map<String, List<Factura>> f5 = facturas.stream()
                .filter(f -> f.getImporte() > 10)
                .collect(Collectors.groupingBy(Factura::getConcepto));

        // COUNT
        Map<String, Long> f6 = facturas.stream()
                .collect(Collectors.groupingBy(Factura::getConcepto, Collectors.counting()));

        // SUMMING
        Map<String,Double> f7 = facturas.stream()
                .collect(Collectors.groupingBy(Factura::getConcepto, Collectors.summingDouble(Factura::getImporte)));

        // ESTADÍSTICAS: CONTEO - SUMA - MÍNIMO - PROMEDIO - MÁXIMO
        DoubleSummaryStatistics f8 = facturas.stream().collect(Collectors.summarizingDouble(Factura::getImporte));

//		// REDUCE -> Optional es una nueva clase que evita nullPointerException
        Optional<Double> f9 = facturas.stream()
                .map(Factura::getImporte)
                .reduce(Double::sum);


        System.out.println(f6);
//		printList(facturas);

    }


    public static void printList(List<?> list) {
        list.forEach(System.out::println);
    }
}

