from flask import Flask
from flask_mysqldb import MySQL
import datetime as dt


class dao(MySQL):
	def __init__(self, app, host='localhost', port=3306, uname='root', upw='root', dbname='GYRO'):
		app.config['MYSQL_HOST'] = host
		app.config['MYSQL_PORT'] = port
		app.config['MYSQL_DB']   = dbname
		app.config['MYSQL_USER'] = uname
		app.config['MYSQL_PASSWORD'] = upw
		MySQL.__init__(self, app)

	# 입력된 날짜의 달에 해당하는 데이터를 일별 평균으로 추출
	def get_day_from_month(self, id, datetime):
		cursor = self.connection.cursor()
		cursor.execute('''
			SELECT DATE(datetime) AS date, 
			COALESCE(AVG(data), 0) AS average 
			FROM GYRO 
			WHERE id = %s AND YEAR(datetime) = YEAR(%s) AND MONTH(datetime) = MONTH(%s) 
			GROUP BY DATE(datetime);
		''', (id, datetime, datetime))
		data = []
		for element in cursor.fetchall():
			date = dt.datetime.strptime(str(element[0]), '%Y-%m-%d')
			day_of_month = date.day
			data.append({'date':str(element[0]), 'dayofmonth':int(date.day), 'average':float(element[1])})
		return data

	# 입력된 날짜의 달에 해당하는 데이터를 주별 평균으로 추출
	def get_week_from_mont(self, id, datetime):
		cursor = self.connection.cursor()
		cursor.execute('''
			SELECT (FLOOR((DayOfMonth(datetime)-1)/7)+1) as dayofweek, AVG(data) as average
			FROM GYRO
			WHERE id = %s AND YEAR(datetime) = YEAR(%s) AND MONTH(datetime) = MONTH(%s)
			GROUP BY (FLOOR((DayOfMonth(datetime)-1)/7)+1);
		''', (id, datetime, datetime))
		data = []
		for element in cursor.fetchall():
			data.append({'dayofweek':int(element[0]), 'average':float(element[1])})
		return data

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
	def set_gyro(self, id, data):
		cursor = self.connection.cursor()
		cursor.execute('''INSERT INTO GYRO (id, datetime, data) VALUES (%s, now(), %s)''', (id, data))
		self.connection.commit()
		return {'id': id, 'data': data}


