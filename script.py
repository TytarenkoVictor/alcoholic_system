import csv
import random
import names
import datetime
import loremipsum


def random_date_between(start_date, end_date):
    time_between_dates = end_date - start_date
    days_between_dates = time_between_dates.days
    random_number_of_days = random.randrange(days_between_dates)
    return start_date + datetime.timedelta(days=random_number_of_days)


def create_data(size):
    begin_date = datetime.date(1999, 1, 1)
    start_date = datetime.date(1950, 1, 1)
    end_date = datetime.date(2001, 1, 1)

    list_alcoholic = []
    list_bed = []
    list_inspector = []
    list_taken = []
    list_passed = []
    list_released = []
    list_escaped = []
    list_bed_alco = []
    list_group_drinking = []
    list_alco_group = []

    list_drinks = [(1, 'whiskey', 40), (2, 'vodka', 40), (3, 'wine', 20), (4, 'sherry', 20), (5, 'port', 35), (6, 'brandy', 35), (7, 'rum', 50), (8, 'gin', 50), (9, 'tequila', 50), (10, 'hock', 40), (11, 'vermouth', 17), (12, 'absinthe', 45), (13, 'rye', 40), (14, 'beer', 10), (15, 'champagne', 12), (16, 'sake', 30)]
    list_department = [(1, 'Criminal Investigation Office', 100), (2, 'Accident Registration Bureau', 30), (3, 'Center for Countering Extremism', 40), (4, 'The operational investigative unit (ORC) to ensure the safety of persons subject to state protection', 75), (5, 'Department of operative-search information', 30), (6, 'Office or Division of Economic Security and Anti-Corruption', 60), (7, 'Department of NC Interpol', 120), (8, 'Dog Training Center', 30)]
    num_of_department = len(list_department)
    num_of_drinks = len(list_drinks)
    num_of_inspectors = size // 5

    # alcoholic, inspector, bed -- size
    for i in range(size):
        bed = (i + 1, random.choice(['yellow', 'red', 'pink', 'blue', 'lemon', 'darkblue', 'white', 'grey']), random.randrange(1, 3))
        random_date = random_date_between(start_date, end_date)
        description = loremipsum.generate_sentence(start_with_lorem=3)[2]
        alcoholic = (i + 1, names.get_first_name(), names.get_last_name(), random_date.strftime("%m-%d-%Y"), description)
        random_date = random_date_between(start_date, end_date)
        inspector = (i + 1, random.randrange(1, num_of_department + 1), names.get_full_name(), random_date.strftime("%m-%d-%Y"))

        list_alcoholic.append(alcoholic)
        list_bed.append(bed)
        list_inspector.append(inspector)

    # all events occured size * 2 times
    for _ in range(size * 10):
        inspector_id = random.randint(1, (num_of_inspectors))
        alco_id = random.randrange(1, (size + 1))
        taken_date = random_date_between(begin_date, end_date)
        bed_id = random.randint(1, size)
        taken = (inspector_id, alco_id, bed_id, taken_date.strftime("%m-%d-%Y"))
        list_taken.append(taken)

        f_passed = random.randint(0, 1)
        f_escaped = random.randint(0, 1)
        f_released = random.randint(0, 1)
        if f_escaped:
            f_released = 0

        for __ in range(random.randint(0, 3)):
            start_date = taken_date
            changed_date = random_date_between(start_date, end_date)
            new_bed_id = random.randint(1, size)
            bed_alco = (alco_id, new_bed_id, inspector_id, changed_date.strftime("%m-%d-%Y"))
            list_bed_alco.append(bed_alco)
            taken_date = changed_date
            bed_id = new_bed_id

        if f_passed:
            start_date = taken_date
            passed_date = random_date_between(start_date, end_date)
            passed = (alco_id, bed_id, passed_date.strftime("%m-%d-%Y"))
            list_passed.append(passed)
            taken_date = passed_date

        if f_escaped:
            start_date = taken_date
            escaped_date = random_date_between(start_date, end_date)
            escaped = (alco_id, bed_id, escaped_date.strftime("%m-%d-%Y"))
            list_escaped.append(escaped)

        if f_released:
            start_date = taken_date
            released_date = random_date_between(start_date, end_date)
            released = (random.randint(1, num_of_inspectors), alco_id, bed_id, released_date.strftime("%m-%d-%Y"))
            list_released.append(released)

    # adding groups
    for i in range(size // 10):
        random_date = random_date_between(begin_date, end_date)
        group = (i + 1, random.randint(1, num_of_drinks), random_date.strftime("%m-%d-%Y"), loremipsum.generate_sentence(start_with_lorem=3)[2])

        for _ in range(random.randint(1, 8)):
            alco_group = (i + 1, random.randint(1, size))
            list_alco_group.append(alco_group)

        list_group_drinking.append(group)

    write_data('alcoholic.csv', list_alcoholic)
    write_data('alcogroup.csv', list_alco_group)
    write_data('alco_released.csv', list_released)
    write_data('alco_passed.csv', list_passed)
    write_data('alco_escaped.csv', list_escaped)
    write_data('drinks.csv', list_drinks)
    write_data('alco_taken.csv', list_taken)
    write_data('bed.csv', list_bed)
    write_data('bed_alco.csv', list_bed_alco)
    write_data('groups.csv',list_group_drinking)
    write_data('inspectors.csv', list_inspector)
    write_data('department.csv', list_department)


def write_data(filename, data):
    with open(filename, 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile)
        spamwriter.writerows(data)


if __name__ == "__main__" :
    create_data(200)
