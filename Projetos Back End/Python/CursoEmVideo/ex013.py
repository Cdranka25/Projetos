dias = float(input("Quantos dias o carro será alugado? "))
km = float(input("Quantos Km rodados?"))
resultado = (dias * 60) + (km*0.15)
print("O valor a pagar é de R$ {}".format(resultado))