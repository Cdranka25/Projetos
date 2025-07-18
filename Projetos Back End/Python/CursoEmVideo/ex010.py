preco = float(input("Qual o valor do produto? "))
percentual = float(input("Qual o percentual de desconto?"))
valorDesconto = (percentual /100) * preco
resultado = preco - valorDesconto
print("Resultado: R${}".format(resultado))
