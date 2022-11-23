import app, math


class InvalidPermissions(Exception):
    pass


class Calculator:
    def add(self, x, y):
        self.check_types(x, y)
        return x + y

    def substract(self, x, y):
        self.check_types(x, y)
        return x - y

    def multiply(self, x, y):
        if not app.util.validate_permissions(f"{x} * {y}", "user1"):
            raise InvalidPermissions("El usuario no tiene permisos")

        self.check_types(x, y)
        return x * y

    def divide(self, x, y):
        self.check_types(x, y)
        if y == 0:
            raise TypeError("La división por cero no es posible")

        return x / y

    def remainder(self, x, y):
        self.check_types(x, y)
        if y == 0:
            raise TypeError("La división por cero no es posible")
        return x % y

    def power(self, x, y):
        self.check_types(x, y)
        if x == 0 and y < 0:
            raise TypeError("Cero no se puede elevar a una potencia negativa")
        return x**y

    def check_types(self, x, y):
        if not isinstance(x, (int, float)) or not isinstance(y, (int, float)):
            raise TypeError("Los parámetros deben ser números.")

    def sqrt(self, x):
        self.check_types(x)

        if x < 0:
            raise TypeError(
                "No se puede calcular la raíz cuadrada de un número negativo sin usar números complejos"
            )

        return math.sqrt(x)

    def logarithm10(self, x):
        self.check_types(x)

        if x <= 0:
            raise TypeError("No se puede calcular log10 de números negativos o cero")

        return math.log10(x)

    def check_types(self, x, y=0):
        if not isinstance(x, (int, float)) or not isinstance(y, (int, float)):
            raise TypeError("Los parámetros deben ser números")


if __name__ == "__main__":  # pragma: no cover
    calc = Calculator()
    resultadd = calc.add(2, 2)
    resultsubstract = calc.substract(2, 1)
    resultdivide = calc.divide(8, 2)
    resultpower = calc.power(2, 4)
    resultremainder = calc.remainder(2, 1)
    resultsquareroot = calc.sqrt(9)
    resultlogarithm10 = calc.logarithm10(2)

    print("resultadd: ", resultadd)
    print("resultsubstract: ", resultsubstract)
    print("resultdivide: ", resultdivide)
    print("resultpower: ", resultpower)
    print("resultremainder: ", resultremainder)
    print("resultsquareroot: ", resultsquareroot)
    print("resultlogarithm10: ", resultlogarithm10)
