#!/usr/bin/python3

import urllib3
from bs4 import BeautifulSoup
import sys
import re

selector = {
	"www.goodreads.com": [".quoteText"],
	"www.brainyquote.com": [".b-qt"],
	"www.santabanta.com": [".display_block td"],
	"www.keepinspiring.me": [".wp-block-quote"],
	"www.morefamousquotes.com": [".quote > p > a", ".quote > script+p > :not(a)"],
	"bayart.org": [".wp-block-quote"],
	"citatis.com": ["blockquote"],
	"quotestats.com": ["blockquote"],
        "upjourney.com": ["h2~p > em"],
	"www.ascensiongateway.com": ["font > ul > li > font"],
	"www.azquotes.com": ["a.title"],
	"www.greatsayings.net": [".quote"]
}

remove = {
	"www.ascensiongateway.com": ["a"],
}

class GetQuotes:
	def __init__(self):
		host = urllib3.get_host(sys.argv[1])[1]
		self.cssSel = selector.get(host)
		self.cssSelRem = remove.get(host)
		self.soup = BeautifulSoup(urllib3.PoolManager().request("GET", sys.argv[1]).data.decode("utf-8"), "html.parser")
		super().__init__()

	def process(self):
		if self.cssSel:
			for s in self.cssSel:
				for a in self.soup.select(s):
					if self.cssSelRem:
						for r in self.cssSelRem:
							for rr in a.findAll(r):
								rr.decompose()
					line  = a.get_text()
					if line:
						line = re.sub("\s+"," ",line)
						line = re.sub("–.*|”|“|$#?[0-9]+\\.\\s+|-|—","",line)
						line = re.sub("[\s|\n]+$", "\n", line)

						print(line)
						print("--")

GetQuotes().process()

