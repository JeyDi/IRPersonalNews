import newspaper
from newspaper import Article


def papersList():
    """
    Define List of Papers to Search
    Configure the paper you want to get infos
    """

    print("Defining Papers List")
    paper_list = {}
    # Politics
    usIndependent_paper = newspaper.build('https://www.independent.co.uk/news/uk/politics',
                                          language='en', memoize_articles=False)
    ukIndependent_paper = newspaper.build('https://www.independent.co.uk/news/world/americas/us-politics',
                                          language='en', memoize_articles=False)
    euronews_paper = newspaper.build('https://www.euronews.com', language='en', memoize_articles=False)

    # Sport
    eurosport_paper = newspaper.build('https://www.eurosport.co.uk', language='en', memoize_articles=False)

    # Tech
    wired_paper = newspaper.build('http://eurosport.com', language='en', memoize_articles=False)

    # Science
    scientificAmerica_paper = newspaper.build('https://www.scientificamerican.com',
                                              language='en', memoize_articles=False)

    # Lifestyle
    vogue_paper = newspaper.build('https://www.vogue.com', memoize_articles=False, language='en')

    paper_list['politics_usIndependent'] = usIndependent_paper
    paper_list['politics_ukIndependent'] = ukIndependent_paper
    paper_list['politics_euronews'] = euronews_paper
    paper_list['sport_eurosport'] = eurosport_paper
    paper_list['tech_wired'] = wired_paper
    paper_list['science_scientificAmerica'] = scientificAmerica_paper
    paper_list['lifestyle_vogue'] = vogue_paper

    return paper_list


def searchArticles(paper_list):
    """
    Search Articles
    INPUT: paper list = paper_name : paper_object
    OUTPUT: list of articles for a single paper in a dictionary of papers
    """

    print("Start searching articles")

    paper_list_articles = {}

    for paper_name, paper_object in paper_list.items():
        print(f'Searching Articles for paper: {paper_name}, brand: {paper_object.brand}')

        list_articles = paper_object.articles

        paper_list_articles[paper_name] = list_articles

    return paper_list_articles


def analyzeArticles(paper_list_articles):
    """
    Analyze Paper Article
    INPUT: list of papers with articles
    OUTPUT: text
    """

    for paper_name, articles in paper_list_articles.items():

        print(f"Start analyzing urls obtained from: {paper_name}")
        counter = 0

        for single_article in articles:
            try:
                # Download a single article
                single_article.download()

                # Parse the article
                single_article.parse()

                counter = counter + 1

                print(f"{counter} : {paper_name} : {single_article.title}")

                # If you want to have additional semantics parse with nlp tecniques
                # single_article.nlp()
                # print(single_article.keywords)

                # If you have analyzed 10 articles, go to the next paper
                if counter >= 10:
                    break

            except Exception as message:
                print(f"Impossible to download the content: {message}")

    return True


def analyzeSingleArticle(input_url):
    """
    Read a single url, download and parse the article
    INPUT: url
    OUTPUT: text of the article and the title
    """
    # Read the article online from url
    try:
        single_article = Article(url=input_url, language='en')

        # Download a single article
        single_article.download()

        # Parse the article
        single_article.parse()

    except Exception as message:
        print(f"Impossible to extract the article: {message}")
        return False

    # Get the title and the text
    article_title = single_article.title
    article_text = single_article.text

    print(f"Article: {input_url} downloaded and parsed: {article_title}")

    return article_title, article_text


def GetPaperCategories(paper_list):
    """
    Get all the categories for one paper
    INPUT: the paper list with = paper_name: paper_object
    OUTPUT: List of paper sub categories into the website
    """
    print("Start searching categories")
    all_paper_category_list = {}

    for paper_name, paper_object in paper_list.items():

        paper_category = []

        for category in paper_object.category_urls():
            paper_category.append(category)

        all_paper_category_list[paper_name] = paper_category

    return all_paper_category_list
