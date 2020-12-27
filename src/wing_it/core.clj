(ns wing-it.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def vehicle-cost-fns
  {:sporche (partial * 0.12 1.5)
   :tayato (partial * 0.07 1.3)
   :sleta (partial * 0.2 0.1)})

(def walking-speed 4)
(def driving-speed 70)

(defn euclidenan-distance
  "Calculate the distance between two points"
  [{from-lat :lat from-lon :lon} {to-lat :lat to-lon :lon}]
  (let [deglen 110.25
        x (Math/pow (- to-lat from-lat) 2)
        y (Math/pow (* (Math/cos to-lat) (- to-lon from-lon)) 2)]
    (* deglen (Math/sqrt (+ x y)))))

(defmulti itinerary 
  "Calculate the distance of travel between two location, and the cost and
   duration based on the type of transport"
  :transport)

(defmethod itinerary :walking 
  [{:keys [from to]}]
  (let [distance (euclidenan-distance from to)
        duration (/ distance walking-speed)]
    {:distance distance
     :cost 0.0
     :duration duration}))

(defmethod itinerary :driving
  [{:keys [from to vehicle]}]
  (let [distance (euclidenan-distance from to)
        cost ((vehicle vehicle-cost-fns) distance)
        duration (/ distance driving-speed)]
    {:distance distance
     :cost cost
     :duration duration}))