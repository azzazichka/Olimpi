import requests
from bs4 import BeautifulSoup
from dataclasses import dataclass, asdict, field
import datetime
import json

START_YEAR = 2024
PAGE_LINK = "https://olimpiada.ru/article/1150"

@dataclass
class Contest:
    title: str = ''
    subjects: list = field(default_factory=list)
    lvl: int = -1
    date_start: str = ''
    date_end: str = ''
    link: str = ''
    low_grade: int = -1
    up_grade: int = -1
    
def getNormDate(s: str) -> datetime.date:
    s = s.replace('\xa0', ' ')

    date = s.split('...')

    if len(date[0].split(' ')) == 1:
        month = date[1].split(' ')[-1]
    else:
        month = date[0].split(' ')[-1]
    
    month = month.lower()
    month_num_dict = {
        'янв': 1, 'фев': 2, 'мар': 3, 'апр': 4,
        'мая': 5, 'июн': 6, 'июл': 7, 'авг': 8,
        'сен': 9, 'окт': 10, 'ноя': 11, 'дек': 12,
    }

    month = month_num_dict[month]
    day = int(date[0].split(' ')[0])

    return datetime.date(START_YEAR, month, day)
    


try:
    with requests.get(PAGE_LINK) as page:
        page.raise_for_status()
        soup = BeautifulSoup(page.text, 'html.parser')
except requests.RequestException as e:
    print(f"Ошибка при запросе главной страницы: {e}")
    exit(1)

text_block = soup.find('div', class_='standart_text_block')
tables = text_block.find_all('table', class_='note_table')[1:]

contests = []

for table_number, table in enumerate(tables):
    rows = table.find_all('tr')[1:]
    for row in rows:
        row = row.find_all('p')
        
        if len(row) == 4:
            title, _, subjects, lvl = row
            subjects = subjects.text.split(', ')
        else:
            title = row[0]
            subjects = ['остальное']
            lvl = row[-1]

        title = title.find('a')
        contest_link = 'https://olimpiada.ru/' + title.get('href')

        try:
            with requests.get(contest_link) as contest_page:
                contest_soup = BeautifulSoup(contest_page.text, 'html.parser')
        except requests.RequestException as e:
            print(f"Ошибка при запросе страницы олимпиады: {e}")
            continue

        contest = Contest(title.text, subjects, lvl.text, link=contest_link)

        grades = contest_soup.find('span', class_='classes_types_a')
        grades = grades.text.split(' ')[0]
        grades = grades.split('–')
        contest.low_grade = grades[0]
        contest.up_grade = grades[-1]

        date_table = contest_soup.find('table', class_='events_for_activity')
        
        if date_table == None: continue
        
        dates = date_table.find_all('a')[2::2]
        
        date_start = getNormDate(dates[0].text)
        date_end = getNormDate(dates[-1].text)
    
        if (date_end < date_start): 
            date_end = datetime.date(date_end.year + 1, date_end.month, date_end.day)
        
        contest.date_start = date_start.isoformat()
        contest.date_end = date_end.isoformat()

        contests.append(contest)

    print(f'Таблица №{table_number + 1} обработана')

with open('contests.json', 'w', encoding='utf-8') as f:
    json.dump([asdict(contest) for contest in contests], f, ensure_ascii=False, indent=4)