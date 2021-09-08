#!/usr/bin/python3

import urllib3
from bs4 import BeautifulSoup
import sys

selector = {"www.goodreads.com": ".quoteText", "www.brainyquote.com": ".b-qt"}

class GetQuotes:
	def __init__(self):
		host = urllib3.get_host(sys.argv[1])[1]
		self.cssSel = selector[host]
		self.soup = BeautifulSoup(urllib3.PoolManager().request("GET", sys.argv[1]).data.decode("utf-8"), "html.parser")
		super().__init__()

	def process(self):
		for a in self.soup.select(self.cssSel):
			print(a.get_text())
			print("--")

GetQuotes().process()

