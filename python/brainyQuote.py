#!/usr/bin/python3

import urllib3
from bs4 import BeautifulSoup
import sys

class BrainyQuote:
	def __init__(self):
		self.soup = BeautifulSoup(urllib3.PoolManager().request("GET", sys.argv[1]).data.decode("utf-8"), "html.parser")
		super().__init__()


	def process(self):
		for a in self.soup.select(".b-qt"):
			print(a.get_text())
			print("--")

BrainyQuote().process()

