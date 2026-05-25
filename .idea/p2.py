from pprint import pprint
import more_itertools

text = "Functional Python Programming reprezinta o paradigma curata si eficienta"

# Extragem cuvintele din text
words = text.split()


# Aceasta functie va extrage prima litera a cuvantului, convertita la minuscula pentru uniformitate.
key_func = lambda word: word[0].lower()

# Definim functia valoare, valoarea din mapare
value_func = lambda word: word


# map_reduce, daca reduce_func este omis, more_itertools grupeaza automat valorile într-o lista.
grouped_res = more_itertools.map_reduce(words, key_func, value_func)

# Sortam dictionarul dupa cheie ( litere )
sorted_algorithm_result = {k: grouped_res[k] for k in sorted(grouped_res.keys())}

print("Rezultatul sortat si grupat:")
pprint(sorted_algorithm_result)