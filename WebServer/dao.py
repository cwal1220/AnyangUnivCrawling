from flask import Flask
from flask_mysqldb import MySQL
import datetime as dt


class dao(MySQL):
	def __init__(self, app, host='localhost', port=3306, uname='root', upw='rootroot', dbname='AYUNIV'):
		app.config['MYSQL_HOST'] = host
		app.config['MYSQL_PORT'] = port
		app.config['MYSQL_DB']   = dbname
		app.config['MYSQL_USER'] = uname
		app.config['MYSQL_PASSWORD'] = upw
		MySQL.__init__(self, app)

	# 입력된 날짜의 년에 해당하는 데이터를 월별 평균으로 추출
	def get_month_from_year(self, id, datetime):
		cursor = self.connection.cursor()
		cursor.execute('''
			SELECT MONTH(datetime) as month, AVG(data) as average
			FROM GYRO
			WHERE id = %s AND YEAR(datetime) = YEAR(%s)
			GROUP BY MONTH(datetime);
		''', (id, datetime))
		data = []
		for element in cursor.fetchall():
			data.append({'month':int(element[0]), 'average':float(element[1])})
		return data

	# Gyro sensor data update
	def set_user(self, id, name, credit, major, level):
		print(id, name, credit, major, level)
		cursor = self.connection.cursor()
		cursor.execute('''REPLACE INTO USER (id, name, credit, major, level) VALUES (%s, %s, %s, %s, %s)''', (id, name, credit, major, level))
		self.connection.commit()
		return {'id': id, 'name': name, 'credit': credit, 'major': major, 'level': level}


