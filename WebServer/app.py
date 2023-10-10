from flask import Flask, request
from dao import dao
import json
from crawling import getStudentData

app = Flask(__name__)
model = dao(app)

@app.route('/login', methods=['POST'])
def on_login():
		ret = {'success': True}
		try:
			data = json.loads(request.data.decode('utf-8'))
			# TODO: DB 삽입 부 추가 구현
			model.set_user((str(data['id'], '456', 4.1, "컴공", 4)
			print(str(data['id']), len(str(data['id'])), str(data['pw']), len((data['pw'])))
			crol_result = getStudentData(str(data['id']), str(data['pw']))
			if crol_result:
				ret['data'] = crol_result
			else:
				ret['success'] = False
		except Exception as e:
				ret['success'] = False
				ret['error'] = str(e)
		finally:
				return json.dumps(ret)

@app.route('/request/day_from_month', methods=['POST'])
def on_request_day_from_month():
	ret = {'success': True}
	try:
		data = json.loads(request.data.decode('utf-8'))
		ret['data'] = model.get_day_from_month(str(data['id']), str(data['datetime']))
	except Exception as e:
		ret['success'] = False
		ret['error'] = str(e)
	finally:
		return json.dumps(ret)

@app.route('/request/week_from_month', methods=['POST'])
def on_request_week_from_mont():
	ret = {'success': True}
	try:
		data = json.loads(request.data.decode('utf-8'))
		ret['data'] = model.get_week_from_mont(str(data['id']), str(data['datetime']))
	except Exception as e:
		ret['success'] = False
		ret['error'] = str(e)
	finally:
		return json.dumps(ret)

@app.route('/request/month_from_year', methods=['POST'])
def on_request_month_from_year():
	ret = {'success': True}
	try:
		data = json.loads(request.data.decode('utf-8'))
		ret['data'] = model.get_month_from_year(str(data['id']), str(data['datetime']))
	except Exception as e:
		ret['success'] = False
		ret['error'] = str(e)
	finally:
		return json.dumps(ret)

@app.route('/update/gyro', methods=['POST'])
def on_update_gyro():
	ret = {'success': True}
	try:
		data = json.loads(request.data.decode('utf-8'))
		ret['data'] = model.set_gyro(str(data['id']), str(data['data']))
	except Exception as e:
		ret['success'] = False
		ret['error'] = str(e)
	finally:
		return json.dumps(ret)

if __name__ == '__main__':
	app.run(host='0.0.0.0', port=3007)
 
