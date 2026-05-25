import functools
import operator

numbers = [1, 21, 75, 39, 7, 2, 35, 3, 31, 7, 8]

#  Eliminarea numerelor < 5 (pastram doar numerele >= 5)
filtered_numbers = list(filter(lambda x: x >= 5, numbers))
print(f"Dupa filtrare (>= 5): {filtered_numbers}")


#  gruparea in perechi, cream un iterator din lista filtrata pentru a umbla prin fiecare
it = iter(filtered_numbers)
pairs = list(zip(it, it))
print(f"Grupate in perechi: {pairs}")

multiplied_pairs = list(map(lambda p: p[0] * p[1], pairs))
print(f"Rezultatul multiplicarii: {multiplied_pairs}")

#  Sumarea rezultatelor utilizand reduce
total_sum = functools.reduce(operator.add, multiplied_pairs, 0)
print(f"Suma finală: {total_sum}")