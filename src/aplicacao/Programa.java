package aplicacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entidades.Produto;

public class Programa {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o caminho do arquivo: ");
		String path = sc.nextLine();
		List<Produto> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] prod = line.split(",");
				String nome = prod[0];
				Double preco = Double.parseDouble(prod[1]);
				list.add(new Produto(nome, preco));
				line = br.readLine();
			}
			double media = list.stream().map(p -> p.getPreco()).reduce(0.0, (x, y) -> x + y) / list.size();
			System.out.println("Média de preços: " + String.format("%.2f", media));

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			List<String> nomes = list.stream().filter(p -> p.getPreco() < media).map(p -> p.getNome())
					.sorted(comp.reversed()).collect(Collectors.toList());
			nomes.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		sc.close();

	}

}
