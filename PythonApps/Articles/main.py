from utilities.papers import papersList, searchArticles, analyzeArticles, analyzeSingleArticle
from utilities.cache import readYMLFile, saveArticleDisk


# Documentation
# https://newspaper.readthedocs.io/en/latest/
# https://newspaper.readthedocs.io/en/latest/user_guide/quickstart.html


def automatic_paper_analyzer():
    """
    Automatic obtain info for a list of online paper
    Warning: this can cause some problems because the online parsing is quite complicated right now.
    You need to manually filter the results after the download
    """
    # Automatic Full Paper analysis and download
    paper_list = papersList()
    paper_articles = searchArticles(paper_list)
    result = analyzeArticles(paper_articles)
    saveArticleDisk(result)

    return result


def analyzeDictPaperItem(k, i):
    """
    Analyze the sub dictionary.
    It's a functional function to avoid code smell :)
    """
    for paper, url in i.items():
        title, text = analyzeSingleArticle(url)
        saveArticleDisk(title, text, k, paper, folder=k, path=None)
    return True


def single_paper_analyzer():
    """
    Analyze the collection of single urls and get the news
    """
    # Manual single URL analysis

    # 1: get the list of urls from yml config file

    url_info = readYMLFile()

    # 2: Download the articles for every topics
    for k, i in url_info.items():
        k = k.lower().strip()
        if k == 'politics':
            analyzeDictPaperItem(k, i)
        elif k == 'science':
            analyzeDictPaperItem(k, i)
        elif k == 'tech':
            analyzeDictPaperItem(k, i)
        elif k == 'sport':
            analyzeDictPaperItem(k, i)
        elif k == 'life':
            analyzeDictPaperItem(k, i)
        else:
            print(f"Impossible analyzing the category {k}, please modify the config file!")

    pass


def main():
    print("-- ONLINE ARTICLE SEARCH  --")

    # automatic_paper_analyzer

    single_paper_analyzer()


if __name__ == '__main__':
    main()
