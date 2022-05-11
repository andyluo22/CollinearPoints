# CollinearPoints :chart_with_upwards_trend:	

## The Problem
One of the challenges in computer vision is analyzing the patterns in computer images.  It is known that computer vision and it's analysis involves **pattern recognition** - discovering patterns given specific **features** or **descriptors** of an image.  The main **objective** of this program is to cleanly recognize every line segment given a set of **euclidean** data points in a plane - ranging from 6 data points to 10000.

![image](https://user-images.githubusercontent.com/68613171/167939564-4be47e15-85d3-415c-b115-9e2d13da3014.png)

**Figure 1:**  _Demonstrates the function of the program.  Through analyzing a set of euclidean data points, line segments are formed given that a set of four or more points are collinear._

## Significance
The main challenges of writing a program is understanding how to implement **comparable data types** and **comparator instances**.  Since the **time complexity**, rather than space complexity contributes to the main **bottleneck** source in the program, the performance when analyzing huge sets of points like 10000 must be of **growth order** ~ **O(n<sup>2</sup> log n)** time.  Thus, two separate versions of analyzing the data points were done to observe just how much slower it was to brute force the analysis instead of figuring out a more elegant way.  The **brute force** class ~ **O(n<sup>4</sup>)** was only able to analyze up to 200 data points, and even then took a few seconds, while the fast and elegant approach for recognizing line segment patterns was able to take a few seconds in analyzing 10000 points.  

## Fast - Stable-Sort Based Implementation
The elegant approach implemented in the **FastCollinearPoints** class analyzes each point in the input array and sorts every other point by passing in a comparator in a sort function that ranks the slopes from most negative to positive in value. The approach is to then to implement a **sliding window** pointer to detect each window of line segments when four or more data points have the same slope relative to the one being analyzed.  Overriding the hashCode and equals method of the **LineSegment** class is also important since a set is used to avoid drawing redundant line segments in the visualizer.

![image](https://user-images.githubusercontent.com/68613171/167945826-99ce1efe-cc5b-4254-83df-0476eea11f38.png)

**Figure 2:**  _The diagram above shows a valid line segment as 4 points are shown to have a slope of 1.  The sliding window approach would slide through to detect this set of points and keep moving if more sets of points have the same slope._

#### Performance :chart_with_upwards_trend: :stopwatch:
The time complexity is proportional to **O(n<sup>2</sup> log n)**.  The space complexity is also done ~ **O(n)**.


