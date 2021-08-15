#!/usr/bin/python3

from html.parser import HTMLParser
import re
import urllib3
import sys

class BrainyQuote(HTMLParser):
	def __init__(self):
		self.quote = False
		super().__init__()

	def handle_starttag(self, tag, attrs):
		if(tag == 'div' and attrs[0][0] == 'style' and attrs[0][1] == 'display: flex;justify-content: space-between'):
			self.quote = True

	def handle_data(self, data):
		if(self.quote):
        		print(re.sub("^\s","",re.sub("\s+", " ", data)))
		self.quote = False

h = BrainyQuote()

http = urllib3.PoolManager()
resp = http.request('GET', sys.argv[1])
h.feed(resp.data.decode('utf-8'))

