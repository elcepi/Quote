#!/usr/bin/python3

import urllib3
from bs4 import BeautifulSoup
import sys

class GoodReads:
	def __init__(self):
		self.soup = BeautifulSoup(urllib3.PoolManager().request("GET", sys.argv[1]).data.decode("utf-8"), "html.parser")
		super().__init__()

	def process(self):
		for a in self.soup.select(".quoteText"):
			print(a.get_text().replace("\n", " "))


h = GoodReads()
h.process()

