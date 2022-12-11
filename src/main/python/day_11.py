from math import floor
from functools import reduce
import itertools

class Monkey:
    monkey_number: int
    items: list[int]
    operation_description: str
    test_description: str
    throw_to_number_if_true: int 
    throw_to_number_if_false: int

    def __init__(self, monkey_description: str) -> None:
        for index in range(len(monkey_description)):
            monkey_description[index] = monkey_description[index].strip()

        self.monkey_number = int(monkey_description[0].split(" ")[1][0])
        self.items = [int(s.split(",")[0]) for s in monkey_description[1].split(" ")[2:]]
        self.operation_description = monkey_description[2] 
        self.test_description = monkey_description[3]
        self.throw_to_number_if_true = int(monkey_description[4].split(" ")[-1])
        self.throw_to_number_if_false = int(monkey_description[5].split(" ")[-1])

    def inspect(self, item) -> tuple[int, int]:
        item = floor(self.operate(item) / 3.0)
        if self.test_is_true(item):
            return self.throw_to_number_if_true, item
        else:
            return self.throw_to_number_if_false, item

    def operate(self, item: int) -> int:
        operand = self.operation_description.strip().split(" ")[-1]
        if operand == "old":
            operand = item
        else:
            operand = int(operand)
        if "*" in self.operation_description:
            return item * operand
        elif "+" in self.operation_description:
            return item + operand

    def test_is_true(self, item: int) -> bool:
        divisor = int(self.test_description.strip().split(" ")[-1])
        is_true = 0 == item % divisor
        return is_true

def main():
    lines = open('inputs/inputDay11', 'r').readlines()
    monkeys: list[Monkey] = parse_lines_to_monkeys(lines)
    inspections_of_monkey: list[int] = [0] * len(monkeys)
    for round in range(20):
        for index in range(len(monkeys)):
            for item_index in range(len(monkeys[index].items)):
                new_monkey, new_worry_level = monkeys[index].inspect(monkeys[index].items[item_index])
                monkeys[new_monkey].items.append(new_worry_level)
            inspections_of_monkey[index] += len(monkeys[index].items)
            monkeys[index].items = []

    monkey_business = reduce(lambda a, b: a * b, sorted(inspections_of_monkey)[-2:])
    print(f"Puzzle answer to part 1 is {monkey_business}")
    print(f"Puzzle answer to part 2 is {None}")

def parse_lines_to_monkeys(lines: list[str]) -> list[Monkey]:
    monkey_descriptions = [list(y) for x, y in itertools.groupby(lines, lambda z: z == "\n") if not x]
    return [Monkey(monkey_description) for monkey_description in monkey_descriptions]

if __name__ == "__main__":
    main()