
import requests
import json
import urllib
from urllib.parse import urljoin
import time

class db_requester():
        def __init__(self, host):
                self.__host = host

        def request(self, path, param):
                url = urllib.parse.urljoin(self.__host, path)
                r = requests.post(url, json=param)
                return json.loads(r.text)

if __name__ == '__main__':
    db = db_requester('http://158.179.161.134:3007')
    print(db.request('/login', {'id':'2019U1132', 'pw':'!@#atlantis771'}))