#!/usr/bin/perl -w

print "\e[1;32m==================================================================================================\n";
print "\e[1;33m## colorTail.pl by David G. Folch ##\n";
print "\e[1;32mColorTail gives color to your console outputs.\n";
print "Optionally accepts a regex parameter, this regex (not casesensitive) will be colored in green\n";
print "Example: tail /var/logs/*.log | perl colorTail.pl\n";
print "Example: tail /var/logs/*.log | perl colorTail.pl myCustomGreenColoredMatchRegexText\n";
print "==================================================================================================\e[0m\n";
$markedText=$ARGV[0];
if (!defined($markedText)) {
	$markedText="A# Z#";  #UN TEXTO QUE SEA MUY POCO PROBLABLE QUE LO ENCUENTRE
}
while(<STDIN>) {
    my $line = $_;
    chomp($line);
    for($line){
        s/fatal.*|error.*|.*exception.*|\tat .*/\e[0;31m$&\e[0m/gi;
        s/$markedText|info.*|.*user-agent=.*|RemoteAddr=.*| interceptors\.[^ ]+| com\.lm\.[^ ]+| util\.[^ ]+| cronjobs\.[^ ]+/\e[1;32m$&\e[0m/gi;
        s/warn.*|ACTION URL=.*/\e[1;33m$&\e[0m/gi;
        s/==>.*<==/\e[1;44m$&\e[0m/gi;
    }
    print $line, "\n";
}

