import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import mean_absolute_error
from sklearn.tree import DecisionTreeRegressor
import joblib

# loading dataset
df = pd.read_csv('pima-indians-diabetes.csv')

# dropping duplicate values
df.drop_duplicates(inplace=True)

# replacing 0s with Nan where true
columns = ['Glucose', 'BloodPressure', 'SkinThickness', 'Insulin', 'BMI']
for col in columns:
    df[col].replace(0, np.NaN, inplace=True)

# filling nan values with mean values
df = df.fillna(df.mean())

# splitting input (X) and output(Y) variables

X = df.drop('Outcome', axis=1)
y = df['Outcome']


# splitting dataset into train test dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, stratify=y, random_state=1, test_size=0.2)

# using Logistic regression model
model = LogisticRegression(random_state=0, max_iter=10000)
model.fit(X_train,y_train)
prediction = model.predict(X_test)
model_mae = mean_absolute_error(y_test,prediction)

print("Our prediction is off by", model_mae, "using Logistic regression")

#using decision trees model
model2 = DecisionTreeRegressor(random_state=1)
model2.fit(X_train,y_train)
prediction2 = model2.predict(X_test)
model2_mae = mean_absolute_error(y_test,prediction2)

print("Our prediction is off by ", model2_mae, "using decision trees model")

#Since, we get better prediction using logistic regression for our testing data, I'm saving
#the model

# fileName = "DiabetesModel.sav"
# joblib.dump(model,fileName)

#tflite format
