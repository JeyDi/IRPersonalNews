from utilities.settings import downloadWithUsername, downloadWithQuerySearch, mergeFiles


# Documentation
# https://github.com/Jefferson-Henrique/GetOldTweets-python


def main():
    print("-- Custom Tweet Downloader --")

    # Keywords are: Politics, Science, Sport, Lifestyle, Tech
    # keywords = ["politics","science","sport","lifesyle","tech"]
    #
    # for k in keywords:
    #     print(f"Downloading tweets for keyword: {k}")

    # Politics
    downloadWithQuerySearch("politics", username="BBCPolitics", collection_name="politics_1", max_tweets=3000)
    downloadWithQuerySearch("politics", username="CNNPolitics", collection_name='politics_2', max_tweets=3000)

    # Tech
    downloadWithQuerySearch("tech", username="verge", collection_name='tech_1', max_tweets=3000)
    downloadWithQuerySearch("tech", username="WiredUk", collection_name='tech_2', max_tweets=3000)

    # Sport
    downloadWithQuerySearch("sport", username="BBCSport", collection_name='sport_1', max_tweets=3000)
    downloadWithQuerySearch("sport", username="Eurosport", collection_name='sport_2', max_tweets=3000)

    # Lifestyle
    downloadWithQuerySearch("lifestyle", username="voguemagazine", collection_name='lifestyle_1', max_tweets=3000)
    downloadWithQuerySearch("lifestyle", username="IndyLife", collection_name='lifestyle_2', max_tweets=3000)

    # Science
    downloadWithQuerySearch("science", username="sciam", collection_name='science_1', max_tweets=3000)
    downloadWithQuerySearch("science", username="sciencemagazine", collection_name='science_2', max_tweets=3000)

    # Merge the result into a single file
    result = mergeFiles()

    return True


if __name__ == '__main__':
    #result = main()
    # Merge the result into a single file
    result = mergeFiles()
    print(result)
