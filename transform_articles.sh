for d in *; do
	echo "user: $d"
	cd $d
	cat politics[a-z-0-9]*.txt > politics.txt
	cat sport[a-z-0-9]*.txt > sport.txt
	cat life[a-z-0-9]*.txt > life.txt
	cat tech[a-z-0-9]*.txt > tech.txt
	cat science[a-z-0-9]*.txt > science.txt
	cd ..
done
