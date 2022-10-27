import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import mean_absolute_error
from sklearn.tree import DecisionTreeRegressor
import joblib

# loading the dataset in panda
df = pd.read_csv("cleveland.csv")
df.columns = ['age', 'sex', 'cp', 'trestbps', 'chol',
              'fbs', 'restecg', 'thalach', 'exang',
              'oldpeak', 'slope', 'ca', 'thal', 'target']
# print(df.loc[1])

# cleaning the dataset

# target: 0 = absence & 1, 2, 3, 4 = present heart disease
# mapping present values to same i.e. 1, Hence, 0= absence and 1= present

df['target'] = df.target.map({0: 0, 1: 1, 2: 1, 3: 1, 4: 1})

# filling null values with mean values
df = df.fillna(df.mean())

# creating train and test datasets

X = df.drop('target', axis=1) #variable to store dataset except the outcome

y = df['target'] #variable to store target outcome

#splitting the dataset into training and testing dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, stratify=y, random_state=1, test_size=0.2)

# Using Logistic regression model
LRM = LogisticRegression(random_state=0, max_iter=10000)
LRM.fit(X_train, y_train)
prediction = LRM.predict(X_test) #predicting X_test dataset
LRM_mae = mean_absolute_error(y_test, prediction) #calculating mean absolute error between outcome predicted by model (prediction) and
                                                # real outcome from datase (y_test)
print("Our prediction is off by", LRM_mae, "using Logistic regression")

# Using decision trees model
DCT_model = DecisionTreeRegressor(random_state=1)
DCT_model.fit(X_train, y_train)
DCT_predict = DCT_model.predict(X_test)
DCT_mae = mean_absolute_error(y_test, DCT_predict)

print("Our prediction is off by ", DCT_mae, "using decision trees model")

# Since, we get better prediction using Logistic regression for our testing data
# I'm saving the model

fileName = 'HeartDiseaseFinalizedModel.sav'
joblib.dump(LRM,fileName)
