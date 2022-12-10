def main():
    lines = open('inputs/inputDay10', 'r').readlines()
    register_at_cycle = calculate_register_at_cycles(lines)
    filtered_registers = filter(lambda cycle: cycle in [20, 60, 100, 140, 180, 220], register_at_cycle)
    sum_of_signal_strengths = sum(map(lambda cycle: cycle * register_at_cycle[cycle], filtered_registers))
    print(f"Puzzle answer to part 1 is {sum_of_signal_strengths}")
    print(f"Puzzle answer to part 2 is:")
    print_to_crt(register_at_cycle)

def calculate_register_at_cycles(lines: list[str]) -> dict[int, int]:
    cycle = 1
    register = 1
    register_at_cycle = dict()
    for line in lines:
        line = line.strip()
        register_at_cycle[cycle] = register
        cycle += 1
        if line.startswith("addx"):
            register_at_cycle[cycle] = register
            cycle += 1
            
            summand = int(line.split(" ")[1])
            register += summand
    return register_at_cycle

def print_to_crt(register_at_cycle: dict[int, int]):
    pixels = []
    crt_width = 40

    for current_cycle in range(1, len(register_at_cycle)):
        current_value = register_at_cycle[current_cycle]
        sprite = (current_value, current_value + 1, current_value + 2)
        pixels.append("#" if current_cycle % crt_width in sprite else ".")

    for index in range(0, 240, crt_width):
        for pixel in pixels[index:index + crt_width - 1]:
            print(pixel, end="")
        print("\n", end="")

if __name__ == "__main__":
    main()