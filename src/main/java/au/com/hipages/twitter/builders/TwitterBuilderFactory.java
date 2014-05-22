package au.com.hipages.twitter.builders;

import au.com.hipages.twitter.builders.twitter4JBuilder.Twitter4JBuilder;

public abstract class TwitterBuilderFactory {
	public static TwitterBuilder getTwitterBuilderInstance() {
		return new Twitter4JBuilder();
	}
}
